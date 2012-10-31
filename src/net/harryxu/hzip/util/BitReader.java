package net.harryxu.hzip.util;

import java.io.*;

import net.harryxu.hzip.Driver;

/**
 * Defines some method about bit-level operation on input stream
 * 
 * @author Harry Xu
 * 
 */
public class BitReader {
	/** The InputStream of this bit reader */
	private InputStream stream;

	/** number of bits left in the byte I read already */
	private int bitsLeft = 0;
	private int currentByte = 0;

	LengthResetHelper streamSeeker;

	private interface LengthResetHelper {
		public long length();

		public void reset();
	}

	public BitReader(final InputStream is) throws IOException {
		if (!is.markSupported())
			throw new RuntimeException("InputStream must support marking");

		stream = is;
		is.mark(0);

		final long length = is.skip(Long.MAX_VALUE);
		// now just reset it back
		try {
			is.reset();
		} catch (IOException e) {
		}

		streamSeeker = new LengthResetHelper() {
			public long length() {
				return length;
			}

			public void reset() {
				try {
					is.reset();
				} catch (IOException e) {
				}
			}
		};
	}

	/**
	 * Create a InputStreamBitReader
	 * 
	 */
	public BitReader(String filename) throws FileNotFoundException {
		this(new File(filename));
	}

	public BitReader(File file) throws FileNotFoundException {
		this(new FileInputStream(file));
	}

	public BitReader(final FileInputStream file) {
		stream = new BufferedInputStream(file);
		streamSeeker = new LengthResetHelper() {
			public long length() {
				try {
					return file.getChannel().size();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}

			public void reset() {
				try {
					file.getChannel().position(0);
					stream = new BufferedInputStream(file);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		};
	}

	public int readBit() {
		if (bitsLeft == 0) {

			try {
				currentByte = stream.read();
			} catch (IOException e) {
			}
			if (currentByte == -1)
				return -1;
			bitsLeft = 8;
		}
		bitsLeft--;
		return (currentByte >>> bitsLeft) & 1;
	}

	public int readBits(int num) {
		if ((num < 0) || (num > 31)) {
			throw new IllegalArgumentException("Number of bits is out of range");
		}

		int bits = 0;
		while (num > 0) {
			if (bitsLeft == 0) {

				try {
					currentByte = stream.read();
				} catch (IOException e) {
				}

				if (currentByte == -1)
					return -1;
				bitsLeft = 8;
			}

			int cbit = Math.min(num, bitsLeft);
			bits = (bits << cbit)
					| ((currentByte >>> (bitsLeft - cbit) & ((1 << cbit) - 1)));
			num -= cbit;
			bitsLeft -= cbit;

		}

		return bits;
	}

	public int readByte() {
		try {
			if (bitsLeft == 0)
				return stream.read();
		} catch (IOException e) {
		}

		return readBits(8);
	}

	public int readBytes(byte[] buf, int start, int count) {
		try {
			if (bitsLeft == 0)
				return stream.read(buf, start, count);
		} catch (IOException e) {
		}

		for (int i = 0; i < count; i++) {
			int b = readByte();
			if (b == -1)
				return i == 0 ? -1 : i;

			buf[start + i] = (byte) b;
		}
		return count;
	}

	public int readInt() throws EOFException {
		byte[] buf = new byte[4];
		readBytes(buf, 0, 4);
		int result = Driver.byte2int(buf);
		return result;
	}

	public int length() {
		long l = streamSeeker.length();
		if (l > Integer.MAX_VALUE)
			throw new RuntimeException(
					"No support for 64 bit file IO. 640K ought to be enough for anybody.");
		return (int) l;
	}

	public void reset() {
		streamSeeker.reset();
	}
}