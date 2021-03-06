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

package uk.ac.cam.gpe21.droidssl.analysis.util;

import soot.ArrayType;
import soot.RefType;

public final class Types {
	public static final RefType STRING = RefType.v("java.lang.String");
	public static final ArrayType STRING_ARRAY = ArrayType.v(STRING, 1);

	public static final RefType ACTIVITY = RefType.v("android.app.Activity");
	public static final RefType VIEW = RefType.v("android.view.View");

	public static final RefType SOCKET = RefType.v("java.net.Socket");
	public static final RefType SOCKET_FACTORY = RefType.v("javax.net.SocketFactory");

	public static final RefType SSL_CONTEXT = RefType.v("javax.net.ssl.SSLContext");
	public static final RefType SSL_SESSION = RefType.v("javax.net.ssl.SSLSession");
	public static final RefType SSL_EXCEPTION = RefType.v("javax.net.ssl.SSLException");
	public static final RefType SSL_SOCKET = RefType.v("javax.net.ssl.SSLSocket");
	public static final RefType SSL_SOCKET_FACTORY = RefType.v("javax.net.ssl.SSLSocketFactory");
	public static final RefType SSL_CERTIFICATE_SOCKET_FACTORY = RefType.v("android.net.SSLCertificateSocketFactory");
	public static final RefType CERTIFICATE_EXCEPTION = RefType.v("java.security.cert.CertificateException");
	public static final RefType X509_CERTIFICATE = RefType.v("java.security.cert.X509Certificate");
	public static final ArrayType X509_CERTIFICATE_ARRAY = ArrayType.v(X509_CERTIFICATE, 1);

	public static final RefType SECURE_RANDOM = RefType.v("java.security.SecureRandom");

	public static final RefType HOSTNAME_VERIFIER = RefType.v("javax.net.ssl.HostnameVerifier");
	public static final RefType ABSTRACT_VERIFIER = RefType.v("org.apache.http.conn.ssl.AbstractVerifier");
	public static final RefType ALLOW_ALL_HOSTNAME_VERIFIER = RefType.v("org.apache.http.conn.ssl.AllowAllHostnameVerifier");
	public static final RefType BROWSER_COMPAT_HOSTNAME_VERIFIER = RefType.v("org.apache.http.conn.ssl.BrowserCompatHostnameVerifier");
	public static final RefType STRICT_HOSTNAME_VERIFIER = RefType.v("org.apache.http.conn.ssl.StrictHostnameVerifier");

	public static final RefType APACHE_SSL_SOCKET_FACTORY = RefType.v("org.apache.http.conn.ssl.SSLSocketFactory");

	public static final RefType TRUST_MANAGER = RefType.v("javax.net.ssl.TrustManager");
	public static final ArrayType TRUST_MANAGER_ARRAY = ArrayType.v(TRUST_MANAGER, 1);
	public static final RefType X509_TRUST_MANAGER = RefType.v("javax.net.ssl.X509TrustManager");

	public static final RefType KEY_MANAGER = RefType.v("javax.net.ssl.KeyManager");
	public static final ArrayType KEY_MANAGER_ARRAY = ArrayType.v(KEY_MANAGER, 1);

	public static final RefType URL = RefType.v("java.net.URL");
	public static final RefType URL_CONNECTION = RefType.v("java.net.URLConnection");
	public static final RefType HTTP_URL_CONNECTION = RefType.v("java.net.HttpURLConnection");
	public static final RefType HTTPS_URL_CONNECTION = RefType.v("javax.net.ssl.HttpsURLConnection");

	private Types() {
		/* to prevent instantiation */
	}
}
