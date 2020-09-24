package com.ifacebox.speech;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

/**
 * <pre>
 * 采样率	支持8000、16000、44100和48000 Hz
 * 采样精度	16 bits
 * 音频格式	仅支持PCM RAW格式
 * 分片规则	建议音频按100ms长度进行切分发送
 * 连接路数	1路代表单个连接每秒发送1秒时长的音频（如果发送过快，超出的数据会被服务端丢弃，造成回显结果异常）
 * </pre>
 */
public class AudioSpeechUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    public static final Image SPEECH = new ImageIcon(AudioSpeechUI.class.getResource("/speech.png")).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
    private JTabbedPane jTabbedPane;
    private JPanel jPanelSpeech;
    private JPanel jPanelSpeechTools;
    private JPanel jPanelSpeechButton;
    private JPanel jPanelConfig;
    private JPanel jPanelConfigField;
    private Box jBoxConfigField;
    private JPanel jPanelConfigTools;
    private JTextArea jTextArea;
    private JButton jButtonSpeechStart;
    private JButton jButtonSpeechStop;
    private JLabel jLabelSpeech;
    private JTextField jTextFieldIp;
    private JTextField jTextFieldPort;
    private JTextField jTextFieldDevId;
    private JTextField jTextFieldDevKey;
    private JTextField jTextFieldSampleRate;
    private JButton jButtonConfigSave;
    private JButton jButtonConfigStart;
    private JButton jButtonConfigStop;
    private AudioSpeechTransfer audioSpeechTransfer;
    private AudioSpeechServer server;
    private AudioSpeechConfig config;

    public AudioSpeechUI() {
        config = new AudioSpeechConfig();
        super.setTitle("实时语音转写");
        super.setIconImage(SPEECH);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.add(jTabbedPane = new JTabbedPane());
        this.init();
        super.pack();
        super.setLocationRelativeTo(null);
        super.setResizable(false);
        server = new AudioSpeechServer(config, new AudioDataCallback() {
            @Override
            public void setText(boolean isFinal, String text) {
                jLabelSpeech.setText(text);
                if (isFinal) {
                    jTextArea.append(text + "\r\n");
                    jTextArea.setCaretPosition(jTextArea.getText().length());
                }
            }
        });
    }

    public void init() {
        jTabbedPane.add("语音转写", jPanelSpeech = new JPanel());
        jTabbedPane.add("服务设置", jPanelConfig = new JPanel());
        jPanelSpeech.setLayout(new BorderLayout());
        jPanelSpeech.add(new JScrollPane(jTextArea = new JTextArea()), BorderLayout.CENTER);
        jTextArea.setLineWrap(true);
        jPanelSpeech.add(jPanelSpeechTools = new JPanel(), BorderLayout.SOUTH);
        jPanelSpeechTools.setLayout(new BorderLayout());
        jPanelSpeechTools.add(jLabelSpeech = new JLabel(new ImageIcon(SPEECH), JLabel.LEFT), BorderLayout.CENTER);
        jPanelSpeechTools.add(jPanelSpeechButton = new JPanel(), BorderLayout.EAST);
        jPanelSpeechButton.setLayout(new FlowLayout());
        jPanelSpeechButton.add(jButtonSpeechStart = new JButton("开始"));
        jButtonSpeechStart.addActionListener(this);
        jPanelSpeechButton.add(jButtonSpeechStop = new JButton("停止"));
        jButtonSpeechStop.addActionListener(this);
        jButtonSpeechStop.setEnabled(false);
        jPanelConfig.setLayout(new BorderLayout());
        jPanelConfig.add(jPanelConfigField = new JPanel(), BorderLayout.CENTER);
        jPanelConfig.add(jPanelConfigTools = new JPanel(), BorderLayout.SOUTH);
        jPanelConfigField.add(jBoxConfigField = Box.createVerticalBox());
        jBoxConfigField.add(Box.createVerticalStrut(10));
        jBoxConfigField.add(addFieldBox("服务器地址：", jTextFieldIp = new JTextField(32)));
        jBoxConfigField.add(Box.createVerticalStrut(1));
        jBoxConfigField.add(addFieldBox("服务器端口：", jTextFieldPort = new JTextField(32)));
        jBoxConfigField.add(Box.createVerticalStrut(1));
        jBoxConfigField.add(addFieldBox("  用户编码：", jTextFieldDevId = new JTextField(32)));
        jBoxConfigField.add(Box.createVerticalStrut(1));
        jBoxConfigField.add(addFieldBox("  用户密钥：", jTextFieldDevKey = new JTextField(32)));
        jBoxConfigField.add(Box.createVerticalStrut(1));
        jBoxConfigField.add(addFieldBox("音频采样率：", jTextFieldSampleRate = new JTextField(32)));
        jPanelConfigTools.setLayout(new FlowLayout());
        jPanelConfigTools.add(jButtonConfigSave = new JButton("保存"));
        jButtonConfigSave.addActionListener(this);
        jPanelConfigTools.add(jButtonConfigStart = new JButton("启动"));
        jButtonConfigStart.addActionListener(this);
        jPanelConfigTools.add(jButtonConfigStop = new JButton("停止"));
        jButtonConfigStop.addActionListener(this);
        jButtonConfigStop.setEnabled(false);

        jTextFieldIp.setText(config.getIp());
        jTextFieldPort.setText(String.valueOf(config.getPort()));
        jTextFieldDevId.setText(config.getDevId());
        jTextFieldDevKey.setText(config.getDevKey());
        jTextFieldSampleRate.setText(String.valueOf(config.getSampleRate()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jButtonSpeechStart) {
            if (!server.isRunning()) {
                JOptionPane.showMessageDialog(this, "请先在服务设置中启动服务！");
            } else {
                jButtonSpeechStart.setEnabled(false);
                jButtonSpeechStop.setEnabled(true);
                audioSpeechTransfer = new AudioSpeechTransfer(server);
                audioSpeechTransfer.start();
            }
        }
        if (e.getSource() == jButtonSpeechStop) {
            jButtonSpeechStart.setEnabled(true);
            jButtonSpeechStop.setEnabled(false);
            if (audioSpeechTransfer != null) {
                audioSpeechTransfer.stop();
                audioSpeechTransfer = null;
            }
        }
        if (e.getSource() == jButtonConfigSave) {
            try {
                config.setIp(jTextFieldIp.getText());
                config.setPort(Integer.parseInt(jTextFieldPort.getText()));
                config.setDevId(jTextFieldDevId.getText());
                config.setDevKey(jTextFieldDevKey.getText());
                config.setSampleRate(Integer.parseInt(jTextFieldSampleRate.getText()));
                server.setAudioSpeechConfig(config);
            } catch (Exception ex) {
            }
        }
        if (e.getSource() == jButtonConfigStart) {
            jButtonConfigStart.setEnabled(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    server.start();
                    jButtonConfigStop.setEnabled(true);
                }
            }).start();
        }
        if (e.getSource() == jButtonConfigStop) {
            jButtonConfigStop.setEnabled(false);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    server.stop();
                    jButtonConfigStart.setEnabled(true);
                }
            }).start();
        }
    }

    public Box addFieldBox(String label, JComponent field) {
        Box box = Box.createHorizontalBox();
        box.add(new JLabel(label));
        box.add(field);
        return box;
    }

    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new AudioSpeechUI().setVisible(true);
    }

}
