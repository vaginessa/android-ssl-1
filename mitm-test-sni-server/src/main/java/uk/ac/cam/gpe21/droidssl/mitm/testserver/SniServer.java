/*
 * Copyright 2013-2014 Graham Edgecombe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.cam.gpe21.droidssl.mitm.testserver;

import uk.ac.cam.gpe21.droidssl.mitm.crypto.cert.CertificateUtils;
import uk.ac.cam.gpe21.droidssl.mitm.crypto.key.KeyUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class SniServer {
	public static void main(String[] args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		System.setProperty("java.net.preferIPv4Stack", "true");

		PrivateKey key = KeyUtils.readPrivateKey(Paths.get("cert.key"));

		X509Certificate ca = CertificateUtils.readCertificate(Paths.get("ca.crt"));

		SniKeyManager keyManager = new SniKeyManager(key);
		keyManager.addChain("default.example.com", new X509Certificate[] {
			CertificateUtils.readCertificate(Paths.get("cert.crt")),
			ca
		});
		keyManager.addChain("test1.example.com", new X509Certificate[] {
			CertificateUtils.readCertificate(Paths.get("cert1.crt")),
			ca
		});
		keyManager.addChain("test2.example.com", new X509Certificate[] {
			CertificateUtils.readCertificate(Paths.get("cert2.crt")),
			ca
		});

		SniServer server = new SniServer(keyManager);
		server.start();
	}

	private final Executor executor = Executors.newCachedThreadPool();
	private final SSLServerSocket serverSocket;

	public SniServer(SniKeyManager keyManager) throws IOException, NoSuchAlgorithmException, KeyManagementException {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(new KeyManager[] {
			keyManager
		}, null, null);
		this.serverSocket = (SSLServerSocket) context.getServerSocketFactory().createServerSocket(12345);
	}

	public void start() throws IOException {
		while (true) {
			SSLSocket socket = (SSLSocket) serverSocket.accept();

			SSLParameters params = socket.getSSLParameters();
			params.setSNIMatchers(Collections.<SNIMatcher>singleton(new SniHostnameMatcher()));
			socket.setSSLParameters(params);

			executor.execute(new EchoRunnable(socket));
		}
	}
}
