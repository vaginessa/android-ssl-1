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

package uk.ac.cam.gpe21.droidssl.mitm.testclient;

import uk.ac.cam.gpe21.droidssl.mitm.crypto.PermissiveTrustManager;
import uk.ac.cam.gpe21.droidssl.mitm.crypto.cert.CertificateUtils;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public final class SniClient {
	public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException, IOException {
		System.setProperty("java.net.preferIPv4Stack", "true");

		String host = args.length >= 1 ? args[0] : null;

		SniClient client = new SniClient(new InetSocketAddress("127.0.0.1", 12345), host);
		client.start();
	}

	private final InetSocketAddress address;
	private final String host;
	private final SSLSocketFactory factory;

	public SniClient(InetSocketAddress address, String host) throws NoSuchAlgorithmException, KeyManagementException {
		this.address = address;
		this.host = host;

		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new TrustManager[] {
			new PermissiveTrustManager()
		}, null);
		this.factory = context.getSocketFactory();
	}

	public void start() throws IOException {
		InetAddress ip = address.getAddress();
		int port = address.getPort();

		try (SSLSocket socket = (SSLSocket) factory.createSocket(new Socket(ip, port), host, port, true)) {
			if (host != null) {
				SSLParameters params = socket.getSSLParameters();
				params.setServerNames(Arrays.<SNIServerName>asList(new SNIHostName(host)));
				socket.setSSLParameters(params);
			}

			socket.startHandshake();

			try (InputStream is = socket.getInputStream();
			     OutputStream os = socket.getOutputStream()) {
				os.write(0xFF);

				if (is.read() != 0xFF)
					throw new IOException("Server did not echo back 0xFF byte");
			}

			Certificate[] chain = socket.getSession().getPeerCertificates();
			X509Certificate leaf = (X509Certificate) chain[0];
			System.out.println(CertificateUtils.extractCn(leaf));
		}
	}
}
