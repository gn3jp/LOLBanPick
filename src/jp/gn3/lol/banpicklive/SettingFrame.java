package jp.gn3.lol.banpicklive;

import generated.LolLobbyLobbyDto;
import generated.LolLobbyLobbyParticipantDto;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;

public class SettingFrame extends JFrame {

    private final BanPickLive main;

    public SettingFrame(BanPickLive main) {
        this.main = main;
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        initComponents();
        setVisible(true);
    }

    public void buttonServerStartActionPerformed(java.awt.event.ActionEvent evt) {
        int port = (Integer) httpServerPort.getValue();
        String ip = main.localip;
        if (onePC.isSelected()) {
            ip = "localhost";
        }
        main.startImageServer(ip, port, (Integer) webSocketServerPort.getValue());
        url.setText("http://" + ip + ":" + port + "/index.html");

        HttpServerPortLabel.setEnabled(false);
        httpServerPort.setEnabled(false);
        WebSocketServerPortLabel.setEnabled(false);
        webSocketServerPort.setEnabled(false);
        onePC.setEnabled(false);
        buttonServerStart.setEnabled(false);

        BlueAdcLabel.setEnabled(true);
        BlueCoachLabel.setEnabled(true);
        BlueJGLabel.setEnabled(true);
        BlueMidLabel.setEnabled(true);
        BlueSupLabel.setEnabled(true);
        BlueTeamLabel.setEnabled(true);
        BlueTeamNameLabel.setEnabled(true);
        BlueTopLabel.setEnabled(true);
        NameSettings.setEnabled(true);
        RedAdcLabel.setEnabled(true);
        RedCoachLabel.setEnabled(true);
        RedJGLabel.setEnabled(true);
        RedMidLabel.setEnabled(true);
        RedSupLabel.setEnabled(true);
        RedTeamLabel.setEnabled(true);
        RedTeamNameLabel.setEnabled(true);
        RedTopLabel.setEnabled(true);
        Separator.setEnabled(true);
        ServerPanel.setEnabled(true);
        URLLabel.setEnabled(true);
        blueAdc.setEnabled(true);
        blueCoach.setEnabled(true);
        blueJG.setEnabled(true);
        blueMid.setEnabled(true);
        blueSup.setEnabled(true);
        blueTeamName.setEnabled(true);
        blueTop.setEnabled(true);
        buttonGetSummonerName.setEnabled(true);
        buttonNameSettingsApply.setEnabled(true);
        buttonURLCopy.setEnabled(true);
        redAdc.setEnabled(true);
        redCoach.setEnabled(true);
        redJG.setEnabled(true);
        redMid.setEnabled(true);
        redSup.setEnabled(true);
        redTeamName.setEnabled(true);
        redTop.setEnabled(true);
        url.setEnabled(true);
    }

    private void buttonURLCopyActionPerformed(java.awt.event.ActionEvent evt) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(url.getText()), null);
    }

    public void buttonGetSummonerNameActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            LolLobbyLobbyDto lobby = main.api.executeGet("/lol-lobby/v2/lobby", LolLobbyLobbyDto.class);
            int blue = 0;
            int red = 0;
            for (LolLobbyLobbyParticipantDto member : lobby.members) {
                String summonerName = member.summonerName;
                if (member.teamId == 100) {
                    switch (blue) {
                        case 0:
                            blueTop.setText(summonerName);
                            break;
                        case 1:
                            blueJG.setText(summonerName);
                            break;
                        case 2:
                            blueMid.setText(summonerName);
                            break;
                        case 3:
                            blueAdc.setText(summonerName);
                            break;
                        case 4:
                            blueSup.setText(summonerName);
                            break;
                    }
                    blue++;
                } else {
                    switch (red) {
                        case 0:
                            redTop.setText(summonerName);
                            break;
                        case 1:
                            redJG.setText(summonerName);
                            break;
                        case 2:
                            redMid.setText(summonerName);
                            break;
                        case 3:
                            redAdc.setText(summonerName);
                            break;
                        case 4:
                            redSup.setText(summonerName);
                            break;
                    }
                    red++;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buttonNameSettingsApplyActionPerformed(java.awt.event.ActionEvent evt) {
        main.gameInfo.blueTeamName = blueTeamName.getText();
        main.gameInfo.redTeamName = redTeamName.getText();
        main.gameInfo.blueTeamCoachName = blueCoach.getText();
        main.gameInfo.redTeamCoachName = redCoach.getText();

        main.gameInfo.setSummonerName(0, blueTop.getText());
        main.gameInfo.setSummonerName(1, blueJG.getText());
        main.gameInfo.setSummonerName(2, blueMid.getText());
        main.gameInfo.setSummonerName(3, blueAdc.getText());
        main.gameInfo.setSummonerName(4, blueSup.getText());
        main.gameInfo.setSummonerName(5, redTop.getText());
        main.gameInfo.setSummonerName(6, redJG.getText());
        main.gameInfo.setSummonerName(7, redMid.getText());
        main.gameInfo.setSummonerName(8, redAdc.getText());
        main.gameInfo.setSummonerName(9, redSup.getText());

        main.socketServer.sendMessage(main.gameInfo.getJson());
//        main.test();
    }

    private void initComponents() {

        ServerPanel = new javax.swing.JPanel();
        HttpServerPortLabel = new javax.swing.JLabel();
        httpServerPort = new javax.swing.JSpinner();
        WebSocketServerPortLabel = new javax.swing.JLabel();
        webSocketServerPort = new javax.swing.JSpinner();
        onePC = new javax.swing.JCheckBox();
        buttonServerStart = new javax.swing.JButton();
        URLLabel = new javax.swing.JLabel();
        url = new javax.swing.JTextField();
        buttonURLCopy = new javax.swing.JButton();
        NameSettings = new javax.swing.JPanel();
        BlueTeamLabel = new javax.swing.JLabel();
        BlueTeamNameLabel = new javax.swing.JLabel();
        blueTeamName = new javax.swing.JTextField();
        BlueCoachLabel = new javax.swing.JLabel();
        blueCoach = new javax.swing.JTextField();
        BlueTopLabel = new javax.swing.JLabel();
        blueTop = new javax.swing.JTextField();
        BlueJGLabel = new javax.swing.JLabel();
        blueJG = new javax.swing.JTextField();
        BlueMidLabel = new javax.swing.JLabel();
        blueMid = new javax.swing.JTextField();
        BlueAdcLabel = new javax.swing.JLabel();
        blueAdc = new javax.swing.JTextField();
        BlueSupLabel = new javax.swing.JLabel();
        blueSup = new javax.swing.JTextField();
        Separator = new javax.swing.JSeparator();
        RedTeamLabel = new javax.swing.JLabel();
        RedTeamNameLabel = new javax.swing.JLabel();
        redTeamName = new javax.swing.JTextField();
        RedCoachLabel = new javax.swing.JLabel();
        redCoach = new javax.swing.JTextField();
        RedTopLabel = new javax.swing.JLabel();
        redTop = new javax.swing.JTextField();
        RedJGLabel = new javax.swing.JLabel();
        redJG = new javax.swing.JTextField();
        RedMidLabel = new javax.swing.JLabel();
        redMid = new javax.swing.JTextField();
        RedAdcLabel = new javax.swing.JLabel();
        redAdc = new javax.swing.JTextField();
        RedSupLabel = new javax.swing.JLabel();
        redSup = new javax.swing.JTextField();
        buttonGetSummonerName = new javax.swing.JButton();
        buttonNameSettingsApply = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N

        ServerPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "サーバー", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Meiryo UI", 0, 12))); // NOI18N
        ServerPanel.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N

        HttpServerPortLabel.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        HttpServerPortLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        HttpServerPortLabel.setText("HTTP Server Port:");

        httpServerPort.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        httpServerPort.setValue(8334);

        WebSocketServerPortLabel.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        WebSocketServerPortLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        WebSocketServerPortLabel.setText("WebSocket Server Port:");

        webSocketServerPort.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        webSocketServerPort.setValue(8335);

        onePC.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        onePC.setSelected(true);
        onePC.setText("1PC");

        buttonServerStart.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        buttonServerStart.setText("サーバーを起動");
        buttonServerStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonServerStartActionPerformed(evt);
            }
        });

        URLLabel.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        URLLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        URLLabel.setText("URL: ");
        URLLabel.setEnabled(false);

        url.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        url.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        url.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        url.setEnabled(false);
        url.setPreferredSize(new java.awt.Dimension(6, 24));

        buttonURLCopy.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        buttonURLCopy.setText("コピー");
        buttonURLCopy.setEnabled(false);
        buttonURLCopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonURLCopyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ServerPanelLayout = new javax.swing.GroupLayout(ServerPanel);
        ServerPanel.setLayout(ServerPanelLayout);
        ServerPanelLayout.setHorizontalGroup(
                ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ServerPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(ServerPanelLayout.createSequentialGroup()
                                                .addComponent(URLLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(url, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(buttonURLCopy))
                                        .addGroup(ServerPanelLayout.createSequentialGroup()
                                                .addComponent(HttpServerPortLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(httpServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(WebSocketServerPortLabel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(webSocketServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                                .addComponent(onePC)
                                                .addGap(18, 18, 18)
                                                .addComponent(buttonServerStart)))
                                .addContainerGap())
        );
        ServerPanelLayout.setVerticalGroup(
                ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(ServerPanelLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(WebSocketServerPortLabel)
                                                .addComponent(webSocketServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(onePC)
                                                .addComponent(buttonServerStart))
                                        .addGroup(ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(HttpServerPortLabel)
                                                .addComponent(httpServerPort, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(ServerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(URLLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(buttonURLCopy)
                                        .addComponent(url, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        NameSettings.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "名前の設定", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Meiryo UI", 0, 12))); // NOI18N
        NameSettings.setEnabled(false);

        BlueTeamLabel.setFont(new java.awt.Font("Meiryo UI", 1, 14)); // NOI18N
        BlueTeamLabel.setForeground(new java.awt.Color(0, 0, 255));
        BlueTeamLabel.setText("青チーム");
        BlueTeamLabel.setEnabled(false);

        BlueTeamNameLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        BlueTeamNameLabel.setText("チーム名 (2,3文字推奨)");
        BlueTeamNameLabel.setEnabled(false);

        blueTeamName.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        blueTeamName.setEnabled(false);

        BlueCoachLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        BlueCoachLabel.setText("コーチ");
        BlueCoachLabel.setEnabled(false);

        blueCoach.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        blueCoach.setEnabled(false);

        BlueTopLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        BlueTopLabel.setText("TOP");
        BlueTopLabel.setEnabled(false);

        blueTop.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        blueTop.setEnabled(false);

        BlueJGLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        BlueJGLabel.setText("JG");
        BlueJGLabel.setEnabled(false);

        blueJG.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        blueJG.setEnabled(false);

        BlueMidLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        BlueMidLabel.setText("MID");
        BlueMidLabel.setEnabled(false);

        blueMid.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        blueMid.setEnabled(false);

        BlueAdcLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        BlueAdcLabel.setText("ADC");
        BlueAdcLabel.setEnabled(false);

        blueAdc.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        blueAdc.setEnabled(false);

        BlueSupLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        BlueSupLabel.setText("SUP");
        BlueSupLabel.setEnabled(false);

        blueSup.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        blueSup.setEnabled(false);

        Separator.setOrientation(javax.swing.SwingConstants.VERTICAL);
        Separator.setAlignmentX(2.0F);
        Separator.setEnabled(false);

        RedTeamLabel.setFont(new java.awt.Font("Meiryo UI", 1, 14)); // NOI18N
        RedTeamLabel.setForeground(new java.awt.Color(255, 0, 0));
        RedTeamLabel.setText("赤チーム");
        RedTeamLabel.setEnabled(false);

        RedTeamNameLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        RedTeamNameLabel.setText("チーム名 (2,3文字推奨)");
        RedTeamNameLabel.setEnabled(false);

        redTeamName.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        redTeamName.setEnabled(false);

        RedCoachLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        RedCoachLabel.setText("コーチ");
        RedCoachLabel.setEnabled(false);

        redCoach.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        redCoach.setEnabled(false);

        RedTopLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        RedTopLabel.setText("TOP");
        RedTopLabel.setEnabled(false);

        redTop.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        redTop.setEnabled(false);

        RedJGLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        RedJGLabel.setText("JG");
        RedJGLabel.setEnabled(false);

        redJG.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        redJG.setEnabled(false);

        RedMidLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        RedMidLabel.setText("MID");
        RedMidLabel.setEnabled(false);

        redMid.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        redMid.setEnabled(false);

        RedAdcLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        RedAdcLabel.setText("ADC");
        RedAdcLabel.setEnabled(false);

        redAdc.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        redAdc.setEnabled(false);

        RedSupLabel.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        RedSupLabel.setText("SUP");
        RedSupLabel.setEnabled(false);

        redSup.setFont(new java.awt.Font("Meiryo UI", 0, 12)); // NOI18N
        redSup.setEnabled(false);

        buttonGetSummonerName.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        buttonGetSummonerName.setText("サモナーネーム取得 (BP開始前に使用できます)");
        buttonGetSummonerName.setEnabled(false);
        buttonGetSummonerName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGetSummonerNameActionPerformed(evt);
            }
        });

        buttonNameSettingsApply.setFont(new java.awt.Font("Meiryo UI", 1, 12)); // NOI18N
        buttonNameSettingsApply.setText("設定を適用");
        buttonNameSettingsApply.setEnabled(false);
        buttonNameSettingsApply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNameSettingsApplyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout NameSettingsLayout = new javax.swing.GroupLayout(NameSettings);
        NameSettings.setLayout(NameSettingsLayout);
        NameSettingsLayout.setHorizontalGroup(
                NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                                .addComponent(BlueTeamLabel)
                                                .addGap(268, 268, 268)
                                                .addComponent(RedTeamLabel)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                                .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                .addComponent(buttonGetSummonerName))
                                                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                                                .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addComponent(blueCoach, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                                                                                .addComponent(BlueTeamNameLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(BlueTopLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(BlueCoachLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(blueTeamName, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(blueTop))
                                                                        .addComponent(BlueJGLabel)
                                                                        .addComponent(BlueMidLabel)
                                                                        .addComponent(BlueAdcLabel)
                                                                        .addComponent(BlueSupLabel))
                                                                .addGap(0, 2, Short.MAX_VALUE))
                                                        .addComponent(blueJG, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(blueMid, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(blueAdc, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(blueSup, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addGap(18, 18, 18)
                                                .addComponent(Separator, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(redJG, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                                                        .addComponent(redMid, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(redAdc, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(redSup, javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                                                .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                                .addComponent(redCoach, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(RedTeamNameLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(RedTopLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(RedCoachLabel, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(redTeamName, javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(redTop, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(RedJGLabel)
                                                                        .addComponent(RedMidLabel)
                                                                        .addComponent(RedAdcLabel)
                                                                        .addComponent(RedSupLabel)
                                                                        .addComponent(buttonNameSettingsApply, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE)))))
                                .addContainerGap())
        );
        NameSettingsLayout.setVerticalGroup(
                NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                                .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(BlueTeamLabel)
                                                        .addComponent(RedTeamLabel))
                                                .addGap(7, 7, 7)
                                                .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                                                .addComponent(RedTeamNameLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(redTeamName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(6, 6, 6)
                                                                .addComponent(RedCoachLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(redCoach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(RedTopLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(redTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(RedJGLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(redJG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(RedMidLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(redMid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(RedAdcLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(redAdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(RedSupLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(redSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(NameSettingsLayout.createSequentialGroup()
                                                                .addComponent(BlueTeamNameLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(blueTeamName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(6, 6, 6)
                                                                .addComponent(BlueCoachLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(blueCoach, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(BlueTopLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(blueTop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(BlueJGLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(blueJG, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(BlueMidLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(blueMid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(BlueAdcLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(blueAdc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(BlueSupLabel)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(blueSup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addComponent(Separator))
                                .addGap(18, 18, 18)
                                .addGroup(NameSettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(buttonGetSummonerName, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(buttonNameSettingsApply, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(ServerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(NameSettings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(ServerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(NameSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0))
        );

        pack();
    }

    private javax.swing.JLabel BlueAdcLabel;
    private javax.swing.JLabel BlueCoachLabel;
    private javax.swing.JLabel BlueJGLabel;
    private javax.swing.JLabel BlueMidLabel;
    private javax.swing.JLabel BlueSupLabel;
    private javax.swing.JLabel BlueTeamLabel;
    private javax.swing.JLabel BlueTeamNameLabel;
    private javax.swing.JLabel BlueTopLabel;
    private javax.swing.JLabel HttpServerPortLabel;
    private javax.swing.JPanel NameSettings;
    private javax.swing.JLabel RedAdcLabel;
    private javax.swing.JLabel RedCoachLabel;
    private javax.swing.JLabel RedJGLabel;
    private javax.swing.JLabel RedMidLabel;
    private javax.swing.JLabel RedSupLabel;
    private javax.swing.JLabel RedTeamLabel;
    private javax.swing.JLabel RedTeamNameLabel;
    private javax.swing.JLabel RedTopLabel;
    private javax.swing.JSeparator Separator;
    private javax.swing.JPanel ServerPanel;
    private javax.swing.JLabel URLLabel;
    private javax.swing.JLabel WebSocketServerPortLabel;
    private javax.swing.JTextField blueAdc;
    private javax.swing.JTextField blueCoach;
    private javax.swing.JTextField blueJG;
    private javax.swing.JTextField blueMid;
    private javax.swing.JTextField blueSup;
    private javax.swing.JTextField blueTeamName;
    private javax.swing.JTextField blueTop;
    private javax.swing.JButton buttonGetSummonerName;
    private javax.swing.JButton buttonNameSettingsApply;
    private javax.swing.JButton buttonServerStart;
    private javax.swing.JButton buttonURLCopy;
    private javax.swing.JSpinner httpServerPort;
    private javax.swing.JCheckBox onePC;
    private javax.swing.JTextField redAdc;
    private javax.swing.JTextField redCoach;
    private javax.swing.JTextField redJG;
    private javax.swing.JTextField redMid;
    private javax.swing.JTextField redSup;
    private javax.swing.JTextField redTeamName;
    private javax.swing.JTextField redTop;
    private javax.swing.JTextField url;
    private javax.swing.JSpinner webSocketServerPort;
}
