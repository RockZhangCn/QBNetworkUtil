package tencent.com.rocksnzhang.qbnetworkutil;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by rock on 16-2-19.
 */
public class BasicInfoFragment extends Fragment
{
    private Context mContext;

    public BasicInfoFragment()
    {

    }

    private String mTitile;
    private TextView mTextView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.basicinfo, container, false);
        mTextView = (TextView)view.findViewById(R.id.fragment_tv);
        mTextView.setText(mTitile);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments!= null)
            mTitile = arguments.getString("titile");
        else
            mTitile = "Default content";

    }
}
