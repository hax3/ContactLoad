package hax3.contactload;

/*
 주소록 가져오기 연습
*/
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


/*
 흐름
 (1) 버튼 클릭 -> 주소록 인텐트 호출
 (2) 연락처 선택 -> 메인으로 복귀(return value(이름, 번호))
 (3) 메인에서 return value 처리
 */
public class MainActivity extends AppCompatActivity {

    Button button;
    String name;
    String number;
    EditText editName;
    EditText editNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        editName = (EditText) findViewById(R.id.editText);
        editNumber = (EditText) findViewById(R.id.editText2);

        //(1) 인텐트 호출하는 온클릭리스너
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);//(1) ~ (2) 연락처 선택
                startActivityForResult(intent, 0);//결과물 리턴받기위한 함수
            }
        });
    }

    /*(3)리턴받은 값 처리*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            Cursor cursor = getContentResolver().query(data.getData(),
                    new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER}, null, null, null);
            cursor.moveToFirst();
            name = cursor.getString(0);        //0은 이름.
            number = cursor.getString(1);   //1은 번호.
            editName.setText(name);
            editNumber.setText(number);
            cursor.close();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
