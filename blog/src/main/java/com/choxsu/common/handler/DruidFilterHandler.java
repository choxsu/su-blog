

package com.choxsu.common.handler;

import com.jfinal.handler.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Pattern;

/**
 * @author choxsu
 */
public class DruidFilterHandler extends Handler {

	private String regex;

	public DruidFilterHandler(String regex) {
		this.regex = regex;
	}

	@Override
	public void handle(String target, HttpServletRequest request, HttpServletResponse response, boolean[] isHandled) {
		assert regex != null;
		Pattern pattern = Pattern.compile(regex);
		if (pattern.matcher(target).find()){
			return;
		}
		next.handle(target, request, response, isHandled);
	}
}
