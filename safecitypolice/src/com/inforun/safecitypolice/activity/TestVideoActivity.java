package com.inforun.safecitypolice.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;
public class TestVideoActivity extends Activity
  implements View.OnClickListener
{
  private static final int MEDIATHREAD = 17;
  private TabHost TabHost = null;
  private Bundle bundle;
  private String data;
  private boolean flag = true;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.arg1)
      {
      default:
        return;
      case 17:
      }
      try
      {
        TestVideoActivity.this.mediaPlayer.reset();
        TestVideoActivity.this.mediaPlayer.setDataSource(TestVideoActivity.this.path);
        TestVideoActivity.this.mediaPlayer.setDisplay(TestVideoActivity.this.surfaceView.getHolder());
        TestVideoActivity.this.mediaPlayer.prepare();
        TestVideoActivity.this.mediaPlayer.setOnPreparedListener(new TestVideoActivity.PreparedListener(
        		TestVideoActivity.this.position));
        return;
      }
      catch (Exception localException)
      {
      }
    }
  };
  int i = 0;
  private Boolean iStart = Boolean.valueOf(true);
  private String id;
  private String imgUrl;
  private Boolean isjz = Boolean.valueOf(false);
  private ImageView issrt;
  private LinearLayout layout1;
  private LinearLayout layout2;
  private String localUrl;
  Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (TestVideoActivity.this.mediaPlayer == null)
    	  TestVideoActivity.this.flag = false;
      while (!TestVideoActivity.this.mediaPlayer.isPlaying())
        return;
      TestVideoActivity.this.flag = true;
      int i = TestVideoActivity.this.mediaPlayer.getCurrentPosition();
      int j = TestVideoActivity.this.mediaPlayer.getDuration();
      int k = TestVideoActivity.this.seekbar.getMax();
      TestVideoActivity.this.seekbar.setProgress(i * k / j);
    }
  };
  private LinearLayout mainLin;
//  private LocalActivityManager manager = null;
  private long mediaLength = 0L;
  private MediaPlayer mediaPlayer;
  private String msg;
  private String path;
  private Boolean pause = Boolean.valueOf(true);
  RelativeLayout pl_rl;
  private int position;
  private String praiseNum;
  private ImageView quanping;
  private long readSize = 0L;
  private RelativeLayout relativeLayout;
  private SeekBar seekbar;
  private ProgressBar spjz;
  private String staticUrl;
  private SurfaceView surfaceView;
  RelativeLayout surface_re;
  private TextView text1;
  private TextView text2;
  private String title;
  private LinearLayout top;
  private String transpondNum;
  private TextView txt_title;
  private upDateSeekBar update;
  private String url;
  private ImageView xiaoping;
  ImageView zan_img;
  TextView zan_num;
  ImageView zf_img;
  TextView zf_num;

  private void init()
  {
    this.bundle = getIntent().getExtras();
    this.id = this.bundle.getString("id");
    this.url = this.bundle.getString("url");
    this.data = this.bundle.getString("data");
    this.msg = this.bundle.getString("msg");
    this.title = this.bundle.getString("title");
    this.imgUrl = this.bundle.getString("imgUrl");
    this.transpondNum = this.bundle.getString("transpondNum");
    this.praiseNum = this.bundle.getString("praiseNum");
    this.staticUrl = this.bundle.getString("staticUrl");
    this.TabHost = ((TabHost)findViewById(2131165199));
    this.layout1 = ((LinearLayout)findViewById(2131165200));
    this.layout2 = ((LinearLayout)findViewById(2131165202));
    this.layout1.setOnClickListener(this);
    this.layout2.setOnClickListener(this);
    this.text1 = ((TextView)findViewById(2131165378));
    this.text2 = ((TextView)findViewById(2131165379));
//    Intent localIntent1 = new Intent(this, IntroduceActivity.class);
//    localIntent1.putExtra("id", this.id);
//    localIntent1.putExtra("data", this.data);
//    localIntent1.putExtra("img", this.url);
//    localIntent1.putExtra("msg", this.msg);
//    localIntent1.putExtra("imgUrl", this.imgUrl);
//    localIntent1.putExtra("transpondNum", this.transpondNum);
//    localIntent1.putExtra("praiseNum", this.praiseNum);
//    localIntent1.putExtra("staticUrl", this.staticUrl);
//    Intent localIntent2 = new Intent(this, CommentActivity.class);
//    localIntent2.putExtra("id", this.id);
//    this.TabHost.setup();
//    this.TabHost.setup(this.manager);
//    this.TabHost.addTab(this.TabHost.newTabSpec("introduce").setIndicator("introduce").setContent(localIntent1));
//    this.TabHost.addTab(this.TabHost.newTabSpec("comment").setIndicator("comment").setContent(localIntent2));
//    this.TabHost.setCurrentTabByTag("introduce");
//    this.zan_img = ((ImageView)findViewById(2131165381));
//    this.zf_img = ((ImageView)findViewById(2131165383));
//    this.zan_img.setOnClickListener(this);
//    this.zf_img.setOnClickListener(this);
//    this.zan_num = ((TextView)findViewById(2131165382));
//    this.zf_num = ((TextView)findViewById(2131165384));
//    if ((this.url == null) || (this.url.equals("")))
//      this.surface_re.setVisibility(8);
  }
  private void initView()
  {
    String str;
    if (Environment.getExternalStorageState().equals("mounted"))
    {
      str = Environment.getExternalStorageDirectory().getAbsolutePath() + "/VideoCache/" + this.url;
      if (!new File(str).exists())
        this.path = this.url;
    }
    while (true)
    {
      this.seekbar = ((SeekBar)findViewById(2131165374));
      this.spjz = ((ProgressBar)findViewById(2131165373));
      this.issrt = ((ImageView)findViewById(2131165372));
      this.issrt.setOnClickListener(this);
      this.surfaceView = ((SurfaceView)findViewById(2131165353));
      this.relativeLayout = ((RelativeLayout)findViewById(2131165371));
      this.quanping = ((ImageView)findViewById(2131165375));
      this.xiaoping = ((ImageView)findViewById(2131165376));
      this.top = ((LinearLayout)findViewById(2131165186));
      findViewById(2131165197).setOnClickListener(this);
      this.txt_title = ((TextView)findViewById(2131165234));
      this.quanping.setOnClickListener(this);
      this.xiaoping.setOnClickListener(this);
      this.txt_title.setText(this.title);
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      int j = localDisplayMetrics.widthPixels;
      int k = localDisplayMetrics.heightPixels;
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
      localLayoutParams.width = j;
      localLayoutParams.height = (k * 2 / 5);
      this.surfaceView.setLayoutParams(localLayoutParams);
      this.surfaceView.getHolder().setFixedSize(localLayoutParams.width, localLayoutParams.height);
      this.surfaceView.getHolder().setKeepScreenOn(true);
      this.surfaceView.getHolder().addCallback(new surfaceCallBack());
      this.surfaceView.setOnClickListener(this);
      this.seekbar.setOnSeekBarChangeListener(new surfaceSeekBar());
      this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
      {
        public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
        {
        	TestVideoActivity.this.issrt.setBackgroundResource(2130837963);
        	TestVideoActivity.this.pause = Boolean.valueOf(true);
        	TestVideoActivity.this.mediaPlayer.seekTo(0);
        	TestVideoActivity.this.seekbar.setProgress(0);
        	TestVideoActivity.this.mediaPlayer.pause();
        }
      });
      this.mainLin = ((LinearLayout)findViewById(2131165377));
      this.pl_rl = ((RelativeLayout)findViewById(2131165380));
      return;
//      TestVideoActivity.this.path = str;
//      continue;
//      Toast.makeText(this, "SD卡不存在！", 0).show();
    }
  }
  private void play(int paramInt)
  {
    //new Thread(new mediaThread(null)).start();
  }
  private void praiseArticle()
  {
    
  }
  private void showProgress()
  {
    this.issrt.setVisibility(4);
    this.spjz.setVisibility(0);
  }

//  private void transpondArticle()
//  {
//    showDig();
//    RequestParams localRequestParams = new RequestParams();
//    localRequestParams.put("articleId", this.id);
//    HttpUtil.get("http://www.lmxy.com/hotMom/ThirdApi/transpondArticle.do", localRequestParams, new JsonHttpResponseHandler()
//    {
//      public void onFailure(Throwable paramAnonymousThrowable)
//      {
//        Toast.makeText(this, "网络发生错误,请稍后再试!", 0).show();
//      }
//      public void onFinish()
//      {
//        this.unShowDig();
//      }
//      public void onSuccess(JSONObject paramAnonymousJSONObject)
//      {
//        try
//        {
//          paramAnonymousJSONObject.getString("Status").equals("0");
//          return;
//        }
//        catch (JSONException localJSONException)
//        {
//          localJSONException.printStackTrace();
//        }
//      }
//    });
//  }
  private void writeMedia()
  {
    new Thread(new Runnable()
    {
      // ERROR //
      public void run()
      {
        // Byte code:
        //   0: aconst_null
        //   1: astore_1
        //   2: aconst_null
        //   3: astore_2
        //   4: new 26 java/net/URL
        //   7: dup
        //   8: aload_0
        //   9: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   12: invokestatic 30 com/hotmom/MediaPlayerActivity:access$1 (Lcom/hotmom/MediaPlayerActivity;)Ljava/lang/String;
        //   15: invokespecial 33 java/net/URL:<init> (Ljava/lang/String;)V
        //   18: astore_3
        //   19: aload_3
        //   20: invokevirtual 37 java/net/URL:openConnection ()Ljava/net/URLConnection;
        //   23: checkcast 39 java/net/HttpURLConnection
        //   26: astore 10
        //   28: aload_0
        //   29: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   32: invokestatic 42 com/hotmom/MediaPlayerActivity:access$18 (Lcom/hotmom/MediaPlayerActivity;)Ljava/lang/String;
        //   35: astore 11
        //   37: aconst_null
        //   38: astore_2
        //   39: aconst_null
        //   40: astore_1
        //   41: aload 11
        //   43: ifnonnull +38 -> 81
        //   46: aload_0
        //   47: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   50: new 44 java/lang/StringBuilder
        //   53: dup
        //   54: invokestatic 50 android/os/Environment:getExternalStorageDirectory ()Ljava/io/File;
        //   57: invokevirtual 56 java/io/File:getAbsolutePath ()Ljava/lang/String;
        //   60: invokestatic 62 java/lang/String:valueOf (Ljava/lang/Object;)Ljava/lang/String;
        //   63: invokespecial 63 java/lang/StringBuilder:<init> (Ljava/lang/String;)V
        //   66: ldc 65
        //   68: invokevirtual 69 java/lang/StringBuilder:append (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   71: aload_3
        //   72: invokevirtual 72 java/lang/StringBuilder:append (Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   75: invokevirtual 75 java/lang/StringBuilder:toString ()Ljava/lang/String;
        //   78: invokestatic 79 com/hotmom/MediaPlayerActivity:access$19 (Lcom/hotmom/MediaPlayerActivity;Ljava/lang/String;)V
        //   81: new 52 java/io/File
        //   84: dup
        //   85: aload_0
        //   86: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   89: invokestatic 42 com/hotmom/MediaPlayerActivity:access$18 (Lcom/hotmom/MediaPlayerActivity;)Ljava/lang/String;
        //   92: invokespecial 80 java/io/File:<init> (Ljava/lang/String;)V
        //   95: astore 12
        //   97: aload 12
        //   99: invokevirtual 84 java/io/File:exists ()Z
        //   102: istore 13
        //   104: aconst_null
        //   105: astore_2
        //   106: aconst_null
        //   107: astore_1
        //   108: iload 13
        //   110: ifne +18 -> 128
        //   113: aload 12
        //   115: invokevirtual 87 java/io/File:getParentFile ()Ljava/io/File;
        //   118: invokevirtual 90 java/io/File:mkdirs ()Z
        //   121: pop
        //   122: aload 12
        //   124: invokevirtual 93 java/io/File:createNewFile ()Z
        //   127: pop
        //   128: aload_0
        //   129: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   132: aload 12
        //   134: invokevirtual 97 java/io/File:length ()J
        //   137: invokestatic 101 com/hotmom/MediaPlayerActivity:access$20 (Lcom/hotmom/MediaPlayerActivity;J)V
        //   140: new 103 java/io/FileOutputStream
        //   143: dup
        //   144: aload 12
        //   146: iconst_1
        //   147: invokespecial 106 java/io/FileOutputStream:<init> (Ljava/io/File;Z)V
        //   150: astore 16
        //   152: aload 10
        //   154: ldc 108
        //   156: ldc 110
        //   158: invokevirtual 114 java/net/HttpURLConnection:setRequestProperty (Ljava/lang/String;Ljava/lang/String;)V
        //   161: aload 10
        //   163: ldc 116
        //   165: new 44 java/lang/StringBuilder
        //   168: dup
        //   169: ldc 118
        //   171: invokespecial 63 java/lang/StringBuilder:<init> (Ljava/lang/String;)V
        //   174: aload_0
        //   175: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   178: invokestatic 122 com/hotmom/MediaPlayerActivity:access$21 (Lcom/hotmom/MediaPlayerActivity;)J
        //   181: invokevirtual 125 java/lang/StringBuilder:append (J)Ljava/lang/StringBuilder;
        //   184: ldc 127
        //   186: invokevirtual 69 java/lang/StringBuilder:append (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   189: invokevirtual 75 java/lang/StringBuilder:toString ()Ljava/lang/String;
        //   192: invokevirtual 114 java/net/HttpURLConnection:setRequestProperty (Ljava/lang/String;Ljava/lang/String;)V
        //   195: aload 10
        //   197: invokevirtual 131 java/net/HttpURLConnection:getInputStream ()Ljava/io/InputStream;
        //   200: astore_2
        //   201: aload_0
        //   202: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   205: aload 10
        //   207: invokevirtual 135 java/net/HttpURLConnection:getContentLength ()I
        //   210: i2l
        //   211: invokestatic 138 com/hotmom/MediaPlayerActivity:access$22 (Lcom/hotmom/MediaPlayerActivity;J)V
        //   214: aload_0
        //   215: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   218: invokestatic 141 com/hotmom/MediaPlayerActivity:access$23 (Lcom/hotmom/MediaPlayerActivity;)J
        //   221: lstore 17
        //   223: lload 17
        //   225: ldc2_w 142
        //   228: lcmp
        //   229: ifne +22 -> 251
        //   232: aload 16
        //   234: ifnull +8 -> 242
        //   237: aload 16
        //   239: invokevirtual 146 java/io/FileOutputStream:close ()V
        //   242: aload_2
        //   243: ifnull +7 -> 250
        //   246: aload_2
        //   247: invokevirtual 149 java/io/InputStream:close ()V
        //   250: return
        //   251: aload_0
        //   252: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   255: astore 19
        //   257: aload 19
        //   259: aload 19
        //   261: invokestatic 141 com/hotmom/MediaPlayerActivity:access$23 (Lcom/hotmom/MediaPlayerActivity;)J
        //   264: aload_0
        //   265: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   268: invokestatic 122 com/hotmom/MediaPlayerActivity:access$21 (Lcom/hotmom/MediaPlayerActivity;)J
        //   271: ladd
        //   272: invokestatic 138 com/hotmom/MediaPlayerActivity:access$22 (Lcom/hotmom/MediaPlayerActivity;J)V
        //   275: sipush 4096
        //   278: newarray byte
        //   280: astore 20
        //   282: aload_2
        //   283: aload 20
        //   285: invokevirtual 153 java/io/InputStream:read ([B)I
        //   288: istore 21
        //   290: iload 21
        //   292: iconst_m1
        //   293: if_icmpne +22 -> 315
        //   296: aload 16
        //   298: ifnull +8 -> 306
        //   301: aload 16
        //   303: invokevirtual 146 java/io/FileOutputStream:close ()V
        //   306: aload_2
        //   307: ifnull +152 -> 459
        //   310: aload_2
        //   311: invokevirtual 149 java/io/InputStream:close ()V
        //   314: return
        //   315: aload 16
        //   317: aload 20
        //   319: iconst_0
        //   320: iload 21
        //   322: invokevirtual 157 java/io/FileOutputStream:write ([BII)V
        //   325: aload_0
        //   326: getfield 17 com/hotmom/MediaPlayerActivity$4:this$0 Lcom/hotmom/MediaPlayerActivity;
        //   329: astore 23
        //   331: aload 23
        //   333: aload 23
        //   335: invokestatic 122 com/hotmom/MediaPlayerActivity:access$21 (Lcom/hotmom/MediaPlayerActivity;)J
        //   338: iload 21
        //   340: i2l
        //   341: ladd
        //   342: invokestatic 101 com/hotmom/MediaPlayerActivity:access$20 (Lcom/hotmom/MediaPlayerActivity;J)V
        //   345: goto -63 -> 282
        //   348: astore 22
        //   350: aload 22
        //   352: invokevirtual 160 java/lang/Exception:printStackTrace ()V
        //   355: goto -73 -> 282
        //   358: astore 4
        //   360: aload 16
        //   362: astore_1
        //   363: aload 4
        //   365: invokevirtual 160 java/lang/Exception:printStackTrace ()V
        //   368: aload_1
        //   369: ifnull +7 -> 376
        //   372: aload_1
        //   373: invokevirtual 146 java/io/FileOutputStream:close ()V
        //   376: aload_2
        //   377: ifnull -127 -> 250
        //   380: aload_2
        //   381: invokevirtual 149 java/io/InputStream:close ()V
        //   384: return
        //   385: astore 8
        //   387: return
        //   388: astore 5
        //   390: aload_1
        //   391: ifnull +7 -> 398
        //   394: aload_1
        //   395: invokevirtual 146 java/io/FileOutputStream:close ()V
        //   398: aload_2
        //   399: ifnull +7 -> 406
        //   402: aload_2
        //   403: invokevirtual 149 java/io/InputStream:close ()V
        //   406: aload 5
        //   408: athrow
        //   409: astore 24
        //   411: return
        //   412: astore 27
        //   414: goto -172 -> 242
        //   417: astore 26
        //   419: goto -169 -> 250
        //   422: astore 9
        //   424: goto -48 -> 376
        //   427: astore 7
        //   429: goto -31 -> 398
        //   432: astore 6
        //   434: goto -28 -> 406
        //   437: astore 25
        //   439: goto -133 -> 306
        //   442: astore 5
        //   444: aload 16
        //   446: astore_1
        //   447: goto -57 -> 390
        //   450: astore 4
        //   452: aconst_null
        //   453: astore_2
        //   454: aconst_null
        //   455: astore_1
        //   456: goto -93 -> 363
        //   459: return
        //
        // Exception table:
        //   from to target type
        //   315 345 348 java/lang/Exception
        //   152 223 358 java/lang/Exception
        //   251 282 358 java/lang/Exception
        //   282 290 358 java/lang/Exception
        //   350 355 358 java/lang/Exception
        //   380 384 385 java/io/IOException
        //   4 37 388 finally
        //   46 81 388 finally
        //   81 104 388 finally
        //   113 128 388 finally
        //   128 152 388 finally
        //   363 368 388 finally
        //   310 314 409 java/io/IOException
        //   237 242 412 java/io/IOException
        //   246 250 417 java/io/IOException
        //   372 376 422 java/io/IOException
        //   394 398 427 java/io/IOException
        //   402 406 432 java/io/IOException
        //   301 306 437 java/io/IOException
        //   152 223 442 finally
        //   251 282 442 finally
        //   282 290 442 finally
        //   315 345 442 finally
        //   350 355 442 finally
        //   4 37 450 java/lang/Exception
        //   46 81 450 java/lang/Exception
        //   81 104 450 java/lang/Exception
        //   113 128 450 java/lang/Exception
        //   128 152 450 java/lang/Exception
      }
    }).start();
  }
//  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
//  {
//    if (paramKeyEvent.getKeyCode() == 4)
//    {
//      if ((paramKeyEvent.getAction() == 0) && (paramKeyEvent.getRepeatCount() == 0))
//      {
//        if (getResources().getConfiguration().orientation != 2)
//          break label51;
//        if (getRequestedOrientation() != 1)
//          setRequestedOrientation(1);
//      }
//      return true;
//      label51: finish();
//      return true;
//    }
//    return super.dispatchKeyEvent(paramKeyEvent);
//  }
  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
    case 2131165372:
    case 2131165375:
//    case 2131165376:
//      do
//      {
//        do
//        {
//          do
//          {
//            return;
//            if (!this.isjz.booleanValue())
//              showProgress();
//            if (this.iStart.booleanValue())
//            {
//              play(0);
//              this.issrt.setBackgroundResource(2130837972);
//              this.iStart = Boolean.valueOf(false);
//              new Thread(this.update).start();
//              return;
//            }
//            if (this.mediaPlayer.isPlaying())
//            {
//              this.mediaPlayer.pause();
//              this.issrt.setBackgroundResource(2130837963);
//              this.pause = Boolean.valueOf(true);
//              return;
//            }
//          }
//          while (!this.pause.booleanValue());
//          this.issrt.setBackgroundResource(2130837972);
//          this.mediaPlayer.start();
//          this.pause = Boolean.valueOf(false);
//          return;
//        }
//        while (getRequestedOrientation() == 0);
//        setRequestedOrientation(0);
//        return;
//      }
//      while (getRequestedOrientation() == 1);
//      setRequestedOrientation(1);
//      return;
    case 2131165353:
      if (this.relativeLayout.getVisibility() == 0)
      {
        this.relativeLayout.setVisibility(8);
        this.top.setVisibility(8);
        return;
      }
      this.relativeLayout.setVisibility(0);
      this.top.setVisibility(0);
      return;
    case 2131165200:
      this.TabHost.setCurrentTabByTag("introduce");
      this.layout1.setBackgroundResource(2130837961);
      this.layout2.setBackgroundColor(Color.parseColor("#ffffffff"));
      this.text1.setTextColor(Color.parseColor("#ffffffff"));
      this.text2.setTextColor(Color.parseColor("#e98585"));
      return;
    case 2131165202:
      this.TabHost.setCurrentTabByTag("comment");
      this.layout2.setBackgroundResource(2130837961);
      this.layout1.setBackgroundColor(Color.parseColor("#ffffffff"));
      this.text2.setTextColor(Color.parseColor("#ffffffff"));
      this.text1.setTextColor(Color.parseColor("#e98585"));
      return;
    case 2131165381:
      praiseArticle();
      return;
//    case 2131165383:
//      showShare(true, null, false);
//      return;
    case 2131165197:
    }
    finish();
  }
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    getWindow().setFlags(1024, 1024);
    if (getResources().getConfiguration().orientation == 2)
    {
      this.relativeLayout.setVisibility(0);
      this.top.setVisibility(8);
      this.quanping.setVisibility(4);
      this.xiaoping.setVisibility(0);
      DisplayMetrics localDisplayMetrics2 = new DisplayMetrics();
      getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics2);
      int m = localDisplayMetrics2.widthPixels;
      int n = localDisplayMetrics2.heightPixels;
      RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
      localLayoutParams2.width = m;
      localLayoutParams2.height = n;
      this.surfaceView.setLayoutParams(localLayoutParams2);
      this.surfaceView.getHolder().setFixedSize(m, n);
      this.mainLin.setVisibility(8);
      this.pl_rl.setVisibility(8);
    }
    while (true)
    {
      super.onConfigurationChanged(paramConfiguration);
      return;
//      if (getResources().getConfiguration().orientation == 1)
//      {
//        this.relativeLayout.setVisibility(0);
//        this.top.setVisibility(8);
//        this.xiaoping.setVisibility(8);
//        this.quanping.setVisibility(0);
//        DisplayMetrics localDisplayMetrics1 = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics1);
//        int j = localDisplayMetrics1.widthPixels;
//        int k = localDisplayMetrics1.heightPixels;
//        RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -1);
//        localLayoutParams1.width = j;
//        localLayoutParams1.height = (k * 2 / 5);
//        this.surfaceView.setLayoutParams(localLayoutParams1);
//        this.surfaceView.getHolder().setFixedSize(localLayoutParams1.width, localLayoutParams1.height);
//        this.mainLin.setVisibility(0);
//        this.pl_rl.setVisibility(0);
//      }
    }
  }
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
//    Application.getInstance();
//    Application.insertActivity(this);
    requestWindowFeature(1);
    getWindow().addFlags(128);
    setContentView(2130903091);
//    this.manager = new LocalActivityManager(this, true);
//    this.manager.dispatchCreate(paramBundle);
//    this.surface_re = ((RelativeLayout)findViewById(2131165370));
    init();
//    if (!NetworkProber.isWifi(this))
//      Toast.makeText(this, "当前不是wifi网络连接", 0).show();
    this.update = new upDateSeekBar();
    this.mediaPlayer = new MediaPlayer();
    initView();
    //getComment();
  }
  protected void onDestroy()
  {
    if (this.mediaPlayer != null)
    {
      this.mediaPlayer.release();
      this.mediaPlayer = null;
      this.iStart = Boolean.valueOf(true);
      this.isjz = Boolean.valueOf(false);
    }
    super.onDestroy();
  }
  private final class PreparedListener
    implements MediaPlayer.OnPreparedListener
  {
    private int position;
    public PreparedListener(int arg2)
    {
      this.position = arg2;
    }
    public void onPrepared(MediaPlayer paramMediaPlayer)
    {
//      this.relativeLayout.setVisibility(8);
//      this.spjz.setVisibility(8);
//      this.issrt.setVisibility(0);
//      this.top.setVisibility(8);
//      this.isjz = Boolean.valueOf(true);
      TestVideoActivity.this.mediaPlayer.start();
      //if ((Boolean.valueOf(this.getSharedPreferences("user_info", 0).getBoolean("isCache", false)).booleanValue()) && (NetworkProber.isWifi(this)))
      TestVideoActivity.this.writeMedia();
      if (this.position > 0)
    	  TestVideoActivity.this.mediaPlayer.seekTo(this.position);
    }
  }
  private class mediaThread
    implements Runnable
  {
    private mediaThread()
    {
    }
    public void run()
    {
      Message localMessage = new Message();
      localMessage.arg1 = 17;
      TestVideoActivity.this.handler.sendMessage(localMessage);
    }
  }
  private final class surfaceCallBack
    implements SurfaceHolder.Callback
  {
    private surfaceCallBack()
    {
    }
    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
    {
    }
    public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
    {
      if (TestVideoActivity.this.position > 0)
      {
    	  TestVideoActivity.this.play(TestVideoActivity.this.position);
        TestVideoActivity.this.position = 0;
      }
    }
    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
    {
      if ((TestVideoActivity.this.mediaPlayer != null) && (TestVideoActivity.this.mediaPlayer.isPlaying()))
      {
    	  TestVideoActivity.this.position = TestVideoActivity.this.mediaPlayer.getCurrentPosition();
    	  TestVideoActivity.this.mediaPlayer.stop();
      }
    }
  }
  private final class surfaceSeekBar
    implements SeekBar.OnSeekBarChangeListener
  {
    private surfaceSeekBar()
    {
    }
    public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean)
    {
    }
    public void onStartTrackingTouch(SeekBar paramSeekBar)
    {
    }
    public void onStopTrackingTouch(SeekBar paramSeekBar)
    {
      int i = TestVideoActivity.this.seekbar.getProgress() * TestVideoActivity.this.mediaPlayer.getDuration() / TestVideoActivity.this.seekbar.getMax();
      TestVideoActivity.this.mediaPlayer.seekTo(i);
    }
  }
  class upDateSeekBar
    implements Runnable
  {
    upDateSeekBar()
    {
    }
    public void run()
    {
    	TestVideoActivity.this.mHandler.sendMessage(Message.obtain());
      if (TestVideoActivity.this.flag)
    	  TestVideoActivity.this.mHandler.postDelayed(TestVideoActivity.this.update, 1000L);
    }
  }
}