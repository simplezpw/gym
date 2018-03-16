package com.vooda.weixin.common.session;

import java.util.Enumeration;

public class StandardSessionFacade implements WxSession {

	/**
	 * Wrapped session object.
	 */
	private WxSession session = null;

	public StandardSessionFacade(StandardSession session) {
		this.session = session;
	}

	public InternalSession getInternalSession() {
		return (InternalSession) session;
	}

	public Object getAttribute(String name) {
		return session.getAttribute(name);
	}

	public Enumeration<String> getAttributeNames() {
		return session.getAttributeNames();
	}

	public void setAttribute(String name, Object value) {
		session.setAttribute(name, value);
	}

	public void removeAttribute(String name) {
		session.removeAttribute(name);
	}

	public void invalidate() {
		session.invalidate();
	}

}
