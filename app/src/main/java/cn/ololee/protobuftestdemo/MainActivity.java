package cn.ololee.protobuftestdemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import cn.ololee.protobuftestdemo.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {
  private ActivityMainBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    useProto();
  }

  private void useProto() {
    PersonProto.Person person = PersonProto.Person.newBuilder()
        .setName("ololee")
        .setAge(25)
        .setSex(true)
        .setAddress("中国四川省成都市武侯区")
        .setEmail("ololeecn@gmail.com")
        .build();
    binding.textView.setOnClickListener(v -> {
      binding.textView.setText(person.getAddress());
    });
  }
}