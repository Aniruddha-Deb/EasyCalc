import android.app.Application;
import android.content.Context;

import com.sensei.easycalc.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault( new CalligraphyConfig.Builder()
                                    .setDefaultFontPath( "fonts/default.ttf" )
                                    .setFontAttrId( R.attr.fontPath )
                                    .build() );
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext( CalligraphyContextWrapper.wrap( base ) );
    }
}
