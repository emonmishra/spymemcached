// Copyright (c) 2006  Dustin Sallings <dustin@spy.net>

package net.spy.memcached.protocol.ascii;

import java.nio.ByteBuffer;

import net.spy.memcached.ops.StatsOperation;

/**
 * Operation to retrieve statistics from a memcached server.
 */
class StatsOperationImpl extends OperationImpl
	implements StatsOperation {

	private static final byte[] MSG="stats\r\n".getBytes();

	private final byte[] msg;
	private final StatsOperation.Callback cb;

	public StatsOperationImpl(String arg, StatsOperation.Callback c) {
		super(c);
		cb=c;
		if(arg == null) {
			msg=MSG;
		} else {
			msg=("stats " + arg + "\r\n").getBytes();
		}
	}

	@Override
	public void handleLine(String line) {
		if(line.equals("END")) {
			cb.receivedStatus(line);
			transitionState(State.COMPLETE);
		} else {
			String[] parts=line.split(" ");
			assert parts.length >= 3;
			cb.gotStat(parts[1], parts[2]);
		}
	}

	@Override
	public void initialize() {
		setBuffer(ByteBuffer.wrap(msg));
	}

	@Override
	protected void wasCancelled() {
		cb.receivedStatus("cancelled");
	}

}
