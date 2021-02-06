package com.atr.restfull.springbootjpa.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rbs.licensemanagement.exception.LicenseManagementException;
import com.rbs.licensemanagement.util.Manifest;

@RestController
public class UtilityController {

	private final Logger logger = LoggerFactory.getLogger(UtilityController.class);

	@Autowired
	private Environment env;

	@GetMapping("/api/util/environment")
	public String[] getEnvironmentProfile() {
		if (env.getActiveProfiles().length == 0) {
			String[] localProfile = { "dev" };
			return localProfile;
		}
		return env.getActiveProfiles();
	}

	@Value("${info.app.timestamp}")
	private String appVersion;

	@GetMapping("/api/util/version")
	// @ApiOperation(value = "Returns the version of the License Management Spring
	// boot App", response = String.class)
	public ResponseEntity<?> getVersion(HttpServletRequest request) {

		try {
			String version = Manifest.getVersion(request.getServletContext());
			System.out.println("#######################1######################### "+version);
			System.out.println("#######################2######################### "+appVersion);
			return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(appVersion);

		} catch (Throwable e) {
			return handleThrowable(e, "getVersion");
		}

	}

	private ResponseEntity<?> handleThrowable(Throwable e, String routineName) {
		String body;
		int httpCode = 500;
		String msg = e.getMessage();
		if (msg == null) {
			msg = e.toString();
		}
		if (e instanceof LicenseManagementException) {
			httpCode = 400;
		} else {
			this.logger.error("Issue calling " + routineName, e);
		}
		body = "{\"status\":" + httpCode + ", \"error\":\"" + e.getClass().getSimpleName() + "\", \"message\": \"" + msg
				+ "\"}";
		return ResponseEntity.status(httpCode).contentType(MediaType.APPLICATION_JSON).body(body);
	}

	/*
	 * Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
	 * Required because of 'mode: history' usage in front-end routing,
	 */

	// @RequestMapping(value = "{_:^(?!index\\.html|api).*$}")
	/*
	 * @RequestMapping(value = "{^(?!\\/?api).+$}") public String redirectApi() {
	 * logger.
	 * info("URL entered directly into the Browser, so we need to redirect...");
	 * return "forward:/"; }
	 */

}
