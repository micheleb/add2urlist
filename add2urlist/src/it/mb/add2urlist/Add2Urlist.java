/**
 * Add2Urlist.java Created on 3 Mar 2014
 * 
 * Copyright 2014 Michele Bonazza <emmepuntobi@gmail.com>
 * 
 * This file is part of Add2Urlist.
 * 
 * Add2Urlist is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * Add2Urlist is distributed in the hope that it will be useful, but WITHOUT
 * ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Add2Urlist. If not, see <http://www.gnu.org/licenses/>.
 */
package it.mb.add2urlist;

import it.mb.add2urlist.R;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class Add2Urlist extends Activity {

	private static final Pattern URL_REGEX = Pattern
			.compile("(http|https)://w{0,3}\\.?[^\\s]+");

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		onNewIntent(getIntent());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onNewIntent(android.content.Intent)
	 */
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent != null) {
			if (intent.hasExtra(Intent.EXTRA_TEXT)) {
				String text = intent.getExtras().getString(Intent.EXTRA_TEXT);
				if (text != null) {
					Matcher matcher = URL_REGEX.matcher(text);
					if (matcher.find()) {
						// we only use the first match, it's unclear what to do
						// if more than one match exist
						String url = matcher.group();
						try {
							Uri dst = Uri
									.parse("http://urli.st/bookmarklet/add?url="
											+ new URI(url).toURL());
							intent = new Intent(Intent.ACTION_VIEW);
							intent.setData(dst);
							startActivity(intent);
							finish();
							return;
						} catch (MalformedURLException e) {
							// the regex should be enough not to have these...
						} catch (URISyntaxException e) {
							// ...but if anything fails, showError() at the end
						}
					}
				}
			}
		}
		showError();
	}

	private void showError() {
		Toast.makeText(this, R.string.malformed_url, Toast.LENGTH_LONG).show();
	}

}
