package tw.org.iii.leo.leoguessnum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {
    private View view;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        view= findViewById(R.id.welcome);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMain();
            }
        });
    }

    //主畫面點入後 進入遊戲頁面的 按鈕事件
    private void gotoMain(){

        //從這裡跳到MainAct的類別 （因為還沒有物件實體 所以寫類別）
        Intent intent = new Intent(this,MainActivity.class);
        //用intent 去startActivity
        startActivity(intent);
        finish();

    }


}
