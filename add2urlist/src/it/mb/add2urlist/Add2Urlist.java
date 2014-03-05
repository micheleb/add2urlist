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
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class Add2Urlist extends Activity {

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
                    try {
                        Uri dst = Uri
                                .parse("http://urli.st/bookmarklet/add?url="
                                        + new URI(text).toURL());
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(dst);
                        startActivity(intent);
                    } catch (MalformedURLException e) {
                        Toast.makeText(this, R.string.malformed_url,
                                Toast.LENGTH_LONG).show();
                    } catch (URISyntaxException e) {
                        Toast.makeText(this, R.string.malformed_url,
                                Toast.LENGTH_LONG).show();
                    }
                    finish();
                }
            }
        }
    }

}
