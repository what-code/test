/*! sysad 2014-06-25 */
function img(e, n, t, a, i, r, o, c, m, d, l, s, u, p) {
	var h = new Image,
	f = e + "?type=" + n + "&adcode=" + t + "&unionName=" + r + "&adId=" + o + "&siteCode=" + c;
	a && (f += "&originAdID=" + a),
	i && (f += "&originUnionName=" + i),
	m && (f += "&t=" + m),
	d && (f += "&cookie=" + d),
	l && (f += "&clientip=" + l),
	u && (f += "&ct=" + u),
	p && (f += "&cp=" + p),
	s && (f += "&fromWhere=" + s),
	h.src = f
}
function sys_FillSlot(e) {
	var n = null,
	t = null,
	a = null,
	i = null,
	r = !1,
	o = "BACKFLOW_RV";
	sys_ADS.length > e && void 0 != sys_ADS[e][0] ? (1 == arguments.length ? e > 0 ? (n = sys_ADS[e - 1][1], t = sys_ADS[e - 1][2], a = sys_ADS[e][2], i = sys_ADS[e][1], r = !0) : 0 == e && sysan_params.isFrameBack && (n = firstAdId, t = firstUnionName, a = sys_ADS[e][2], i = sys_ADS[e][1], r = !0) : 3 == arguments.length && (n = arguments[1], t = arguments[2], a = sys_ADS[e][2], i = sys_ADS[e][1], r = !0), document.write(sys_ADS[e][0])) : 1 == arguments.length ? (n = sys_ADS[e - 1][1], t = sys_ADS[e - 1][2], a = "0", i = 0, r = !0) : 3 == arguments.length && (n = arguments[1], t = arguments[2], a = "0", i = 0, r = !0),
	r && (img(backUrl, o, adcode, n, t, a, i, siteCode), img(stormUrl, o, adcode, n, t, a, i, siteCode, requestStartTime, cookieval, ip))
}
function sys_init_param(e) {
	level = e.level,
	confirmUrl = e.confirmUrl,
	backUrl = e.backUrl,
	stormUrl = e.stormUrl,
	siteCode = e.siteCode,
	adcode = e.adcode,
	cookieval = e.cookieval,
	ip = e.ip,
	firstAdId = e.firstAdId,
	firstUnionName = e.firstUnionName,
	ct = e.ct,
	cp = e.cp
}
function sys_first_show() {
	if (null != sys_ADS[level]) {
		sys_FillSlot(level);
		var e = encodeURIComponent(encodeURIComponent(function() {
			return window.parent != window ? document.referrer: location.href
		} ()));
		img(confirmUrl, "CV", adcode, null, null, firstUnionName, firstAdId, siteCode, null, null, null, e),
		img(stormUrl, "CV", adcode, null, null, firstUnionName, firstAdId, siteCode, requestStartTime, cookieval, ip, null, ct, cp)
	}
	img(stormUrl, "RV", adcode, null, null, firstUnionName, firstAdId, siteCode, requestStartTime, cookieval, ip, null, ct, cp)
}
function sys_show() {
	sys_init_param(sysan_params),
	sysan_params.isFrameBack ? ("y" == sysan_params.isTheEnd && sys_FillSlot(level), "n" == sysan_params.isTheEnd && sys_FillSlot(level, firstAdId, firstUnionName)) : sys_first_show()
}
var level = 0,
confirmUrl = "",
backUrl = "",
adcode = "",
stormUrl = "",
siteCode = "",
requestStartTime = (new Date).getTime(),
cookieval = "",
ip = "",
firstAdId = 0,
firstUnionName = "",
ct = 0,
cp = 0;
sys_show();
