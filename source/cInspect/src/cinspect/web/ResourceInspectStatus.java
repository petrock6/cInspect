package cinspect.web;

import cinspect.inspector.statuses.AppDoSInspectorStatus;
import cinspect.inspector.statuses.CCInspectorStatus;
import cinspect.inspector.statuses.LFIInspectorStatus;
import cinspect.inspector.statuses.PHPInfoInspectorStatus;
import cinspect.inspector.statuses.RCEInspectorStatus;
import cinspect.inspector.statuses.RFIInspectorStatus;
import cinspect.inspector.statuses.SQLInspectorStatus;
import cinspect.inspector.statuses.SSNInspectorStatus;
import cinspect.inspector.statuses.TimedSQLInspectorStatus;
import cinspect.inspector.statuses.UDRJSInspectorStatus;
import cinspect.inspector.statuses.XSSInspectorStatus;

public class ResourceInspectStatus {
	private AppDoSInspectorStatus appDoS;
	private CCInspectorStatus cc;
	private LFIInspectorStatus lfi;
	private PHPInfoInspectorStatus phpinfo;
	private RCEInspectorStatus rce;
	private RFIInspectorStatus rfi;
	private SQLInspectorStatus sql;
	private SSNInspectorStatus ssn;
	private TimedSQLInspectorStatus timedSQL;
	private UDRJSInspectorStatus udrjs;
	private XSSInspectorStatus xss;
	
	ResourceInspectStatus() {
		appDoS = AppDoSInspectorStatus.NOT_INSPECTED;
		cc = CCInspectorStatus.NOT_INSPECTED;
		lfi = LFIInspectorStatus.NOT_INSPECTED;
		phpinfo = PHPInfoInspectorStatus.NOT_INSPECTED;
		rce = RCEInspectorStatus.NOT_INSPECTED;
		rfi = RFIInspectorStatus.NOT_INSPECTED;
		sql = SQLInspectorStatus.NOT_INSPECTED;
		ssn = SSNInspectorStatus.NOT_INSPECTED;
		timedSQL = TimedSQLInspectorStatus.NOT_INSPECTED;
		udrjs = UDRJSInspectorStatus.NOT_INSPECTED;
		xss = XSSInspectorStatus.NOT_INSPECTED;
	}
	
	public synchronized AppDoSInspectorStatus getAppDoS() {
		return appDoS;
	}
	public synchronized void setAppDoS(AppDoSInspectorStatus appDoS) {
		this.appDoS = appDoS;
	}
	public synchronized CCInspectorStatus getCc() {
		return cc;
	}
	public synchronized void setCc(CCInspectorStatus cc) {
		this.cc = cc;
	}
	public synchronized LFIInspectorStatus getLfi() {
		return lfi;
	}
	public synchronized void setLfi(LFIInspectorStatus lfi) {
		this.lfi = lfi;
	}
	public synchronized PHPInfoInspectorStatus getPhpinfo() {
		return phpinfo;
	}
	public synchronized void setPhpinfo(PHPInfoInspectorStatus phpinfo) {
		this.phpinfo = phpinfo;
	}
	public synchronized RCEInspectorStatus getRce() {
		return rce;
	}
	public synchronized void setRce(RCEInspectorStatus rce) {
		this.rce = rce;
	}
	public synchronized RFIInspectorStatus getRfi() {
		return rfi;
	}
	public synchronized void setRfi(RFIInspectorStatus rfi) {
		this.rfi = rfi;
	}
	public synchronized SQLInspectorStatus getSql() {
		return sql;
	}
	public synchronized void setSql(SQLInspectorStatus sql) {
		this.sql = sql;
	}
	public synchronized SSNInspectorStatus getSsn() {
		return ssn;
	}
	public synchronized void setSsn(SSNInspectorStatus ssn) {
		this.ssn = ssn;
	}
	public synchronized TimedSQLInspectorStatus getTimedSQL() {
		return timedSQL;
	}
	public synchronized void setTimedSQL(TimedSQLInspectorStatus timedSQL) {
		this.timedSQL = timedSQL;
	}
	public synchronized UDRJSInspectorStatus getUDRJS() {
		return udrjs;
	}
	public synchronized void setUdrjs(UDRJSInspectorStatus udrjs) {
		this.udrjs = udrjs;
	}
	public synchronized XSSInspectorStatus getXss() {
		return xss;
	}
	public synchronized void setXss(XSSInspectorStatus xss) {
		this.xss = xss;
	}
}
