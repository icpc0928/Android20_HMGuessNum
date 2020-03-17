package tw.org.iii.leo.leoguessnum;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0;
    private TextView num1,num2,num3,num4;
    private Button guess;
    private TextView log;
    private String answer;
    private AlertDialog alertDialog = null;
    private int counter = 0 ,times = 10;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView  配置
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
//        0-9按鈕 配置
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn0 = findViewById(R.id.btn0);

        guess = findViewById(R.id.guess);
        log = findViewById(R.id.log);
        log.setMovementMethod(ScrollingMovementMethod.getInstance());

        initNewGame();



    }



//    多按鈕監聽 0-9按鈕
    public void btnNum(View view) {
        int n ;
        int i = whichTextView();
        int btn[] = {btn0.getId(),btn1.getId(),btn2.getId(),btn3.getId(),btn4.getId()
                     ,btn5.getId(),btn6.getId(),btn7.getId(),btn8.getId(),btn9.getId()};

        for( n = 0 ; n<10;n++){
            if(view.getId()==btn[n]){
                break;
            }
        }
        //檢查是否重複按鈕
        CharSequence checkText = ""+n;

        if (!num1.getText().equals(checkText) && !num2.getText().equals(checkText) && !num3.getText().equals(checkText) && !num4.getText().equals(checkText) ){
            chooseNum(n,i);
        }
    }
    //目前輸入幾個號碼
    public int whichTextView(){
        int i;
        int numTV[] = {num1.length(),num2.length(),num3.length(),num4.length()};
        for( i = 0 ; i < 4 ; i++){
            if(numTV[i] == 0){
                break;
            }
        }
        return i;
    }
    //輸入按鈕
    public void chooseNum(int n,int i){



        String str[]={"0","1","2","3","4","5","6","7","8","9"};

        switch (i){
            case 0:
                num1.setText(str[n]);
                break;
            case 1:
                num2.setText(str[n]);
                break;
            case 2:
                num3.setText(str[n]);
                break;
            case 3:
                num4.setText(str[n]);
                break;
        }
    }

    // <- 刪除一碼按鈕 事件
    public void btnBack(View view) {
        int i = whichTextView();
        switch (i){
            case 1:
                num1.setText("");
                break;
            case 2:
                num2.setText("");
                break;
            case 3:
                num3.setText("");
                break;
            case 4:
                num4.setText("");
                break;
        }
    }
    //  清空按鈕 事件
    public void btnClear(View view) {
        num1.setText("");
        num2.setText("");
        num3.setText("");
        num4.setText("");
    }

    // 送出按鈕 事件
    public void guess(View view) {
        int offset = log.getLineCount()*log.getLineHeight();

        //檢查是否已經輸入四碼
        if(!(whichTextView()==4)){
           return;
        }
        counter++;
        Log.v("leo","counter = "+counter);
        String strInput = (String)num1.getText() +num2.getText()+
                num3.getText()+num4.getText();



        String result = checkAB(strInput);

        log.append(counter+" / "+ times +" : "+ strInput + " => " + result +"\n" );

        if(offset>log.getHeight()){
            log.scrollTo(0,offset-log.getHeight());
        }


        btnClear(null);

        if(result.equals("4A0B")){
            showDialog(true,"You Win!");
        }else if (counter == times ){
            showDialog(false,"Answer is " +answer);
        }

    }

    //開新局 ， 重玩
    private void initNewGame(){
        counter = 0;
        answer = createAnswer(4);
        btnClear(null);
        log.setText("");
        Log.v("leo","ans: "+answer);

    }
    //創造答案
    private String createAnswer(int d){
        int[] poker = new int[10];
        for (int i=0; i<poker.length; i++)poker[i]=i;

        for (int i = poker.length-1; i>0; i--) {
            int rand = (int)(Math.random()*(i+1));
            int temp = poker[rand];
            poker[rand] = poker[i];
            poker[i] = temp;
        }

        StringBuffer sb = new StringBuffer();
        for (int i=0; i<d; i++) {
            sb.append(poker[i]);
        }
        return sb.toString();
    }
    //檢查答案
    private String checkAB(String g){
        int A, B; A = B = 0;
        for (int i=0; i<answer.length(); i++) {
            if (answer.charAt(i) == g.charAt(i)) {
                A++;
            }else if(answer.indexOf(g.charAt(i)) != -1) {
                B++;
            }
        }
        return A + "A" + B + "B";
    }

    //重玩按鈕 事件
    public void replay(View view) {
        initNewGame();
    }

    //跳出對話筐視窗
    private void showDialog(boolean isWinner,String mesg){
        //Builder又是一個類別（類別中的類別） ,new出來後傳遞參數的值： 存活在的這個Activity（this)
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(isWinner?"Winner":"Loser");
        builder.setMessage(mesg);
        //不能取消 (不能點空白取消或返回取消） 另增加ＯＫ按鈕 且新增定義在Dialog中的OnClickListener
        builder.setCancelable(false);
        builder.setPositiveButton(isWinner?"下一關":"重玩", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                initNewGame();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();

    }
}

