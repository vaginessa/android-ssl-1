package uk.ac.cam.gpe21.droidssl.mitm;

import javax.net.ssl.SSLSocket;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.Socket;

public final class FileDescriptors {
	private static final Class<?> SSL_SOCKET_IMPL;
	private static final Field SOCK_INPUT;
	private static final Field FD;

	static {
		try {
			SSL_SOCKET_IMPL = Class.forName("sun.security.ssl.SSLSocketImpl");

			SOCK_INPUT = SSL_SOCKET_IMPL.getDeclaredField("sockInput");
			SOCK_INPUT.setAccessible(true);

			FD = FileDescriptor.class.getDeclaredField("fd");
			FD.setAccessible(true);
		} catch (NoSuchFieldException | ClassNotFoundException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static int getFd(Socket socket) throws IOException {
		try {
			Object in = socket instanceof SSLSocket ? SOCK_INPUT.get(socket) : socket.getInputStream();
			if (!(in instanceof FileInputStream))
				throw new IOException("sockInput is not an instance of FileInputStream");

			FileInputStream fin = (FileInputStream) in;
			FileDescriptor fd = fin.getFD();

			return FD.getInt(fd);
		} catch (IllegalAccessException ex) {
			throw new IOException(ex);
		}
	}

	private FileDescriptors() {
		/* empty */
	}
}
