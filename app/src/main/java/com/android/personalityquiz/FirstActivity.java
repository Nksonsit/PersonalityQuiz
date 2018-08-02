package com.android.personalityquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    private TextView txtTitle;
    private EditText edtQue;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setText("أستمرار");

        txtTitle = (TextView)findViewById(R.id.txtTitle);
        edtQue = (EditText)findViewById(R.id.edtQue);
        txtTitle.setText("ﻳﻌﻨﻲ إﻳﻪ اﻟﻘﺎﻟﺐ ﻏﺎﻟﺐ؟");
        txtTitle.setTypeface(Functions.getRegularFont(this));
        btnSubmit.setTypeface(Functions.getRegularFont(this));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtQue.getText().toString().trim().length()==0){
                    Toast.makeText(FirstActivity.this,"من فضلك ادخل اجابه",Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                user.setQuestion(edtQue.getText().toString().trim());
                Intent intent=new Intent(FirstActivity.this,SecondActivity.class);
                intent.putExtra("user",user);
                Functions.fireIntent(FirstActivity.this,intent,true);
                finish();
            }
        });
    }
}
