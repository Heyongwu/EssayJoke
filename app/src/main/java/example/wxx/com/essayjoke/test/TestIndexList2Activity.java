package example.wxx.com.essayjoke.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.wxx.com.essayjoke.R;
import example.wxx.com.essayjoke.test.bean.BUser;
import example.wxx.com.essayjoke.ui.LeaguerRecylerView;

public class TestIndexList2Activity extends AppCompatActivity {

    @BindView(R.id.indexRv)
    LeaguerRecylerView mIndexRv;

    List<BUser> mBUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_index_list2);
        ButterKnife.bind(this);
        setDatas();
    }

    private void setDatas() {
        mBUsers = new ArrayList<>();
        BUser bUser1 = new BUser("哈哈", "1234567811");
        BUser bUser2 = new BUser("o哈", "1234567812");
        BUser bUser3 = new BUser("a哈", "1234567813");
        BUser bUser4 = new BUser("b哈", "1234567814");
        BUser bUser5 = new BUser("e哈", "1234567815");
        BUser bUser6 = new BUser("s哈", "1234567816");
        BUser bUser7 = new BUser("q哈", "1234567817");
        BUser bUser8 = new BUser("h哈", "1234567818");
        BUser bUser9 = new BUser("l哈", "1234567819");
        BUser bUser10 = new BUser("a哈", "1234567810");
        BUser bUser11 = new BUser("b哈", "1234567832");
        mBUsers.add(bUser1);
        mBUsers.add(bUser2);
        mBUsers.add(bUser3);
        mBUsers.add(bUser4);
        mBUsers.add(bUser5);
        mBUsers.add(bUser6);
        mBUsers.add(bUser7);
        mBUsers.add(bUser8);
        mBUsers.add(bUser9);
        mBUsers.add(bUser10);
        mBUsers.add(bUser11);
        mIndexRv.setDatas(mBUsers);
    }
}
