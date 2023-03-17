# config.yml 設定方法
## デフォルト設定
```yml
TOKEN: XXXXXXXXXXX.XXXXXX.XXXXXXXXXXX
SERVER_ID: XXXXXXXXXXXXXXXXXX
BOT_STATUS: "hogehoge"
MESSAGE_SYNC_CHANNEL: XXXXXXXXXXX
WHITELIST_CHANNEL: XXXXXXXXXXX
WHITELIST:
  MULTIPLE: false
  JOIN_GAME:
    BOOL: false
    TYPE: "TXT"
    CHANNEL: XXXXXXXXXXX
  CMD:
    BOOL: false
    SHOW_ALL: false
    ALLOW_MC_ONLY: true
    CHANNEL: XXXXXXXXXXX
SERVER_STATUS:
  SYNC_BOOL: false
  SYNC_CHANNEL: XXXXXXXXXXX
  ENABLE_TEXT: "🟢サーバーが起動中"
  DISABLE_TEXT: "🔴サーバーが停止中"
SERVER_INFO:
  SERVER_NOTICE_BOOL : true
  STAT_MESS : ":green_circle: サーバーが起動しました"
  CLOSE_MESS : ":red_circle: サーバーが停止しました"
USER_INFO:
  USER_NOTICE_BOOL : true
  JOIN_NOTICE:
    TYPE: "embed"
    MESSAGE: "&{UserName}がゲームに参加しました!いらっしゃい!"
    COLOR: "#37ab58"
  LEAVE_NOTICE:
    TYPE: "embed"
    MESSAGE : "&{UserName}がゲームを離れました。バイバイ"
    COLOR: "#c44949"
MESSAGE_TYPE:
  D2M : "<&{UserName}>&{message}"
  M2D : "<&{UserName}>&{message}"
  DISCORD_ONLY : "¥"
  MINECRAFT_ONLY : "*"
CMD:
  CMD_NOTICE_BOOL : true
  CMD_NOTICE_MESS : "がコマンドを利用した! : "
ORE:
  GET_NOTICE:
    SEND_MINECRAFT: true
    SEND_DISCORD: true
    NOTICE_LIST:
      - name: "DIAMOND_ORE"
        sendText: "&{UserName}がダイヤモンドを見つけた!!"
      - name: "DEEPSLATE_DIAMOND_ORE"
        sendText: "&{UserName}が深成岩のダイヤモンドを見つけた!!"
BED:
  SLEEP_NOTICE: true
```

## 各種項目内容
|key name|type|about|default|
|----|----|----|----|
|TOKEN|String|Discord Token|XXXXXXXXXXX.XXXXXX.XXXXXXXXXXX|
|BOT_STATUS|String|Botのステータス|hogehoge|
|MESSAGE_SYNC_CHANNEL|String|メッセージを送信するチャンネル|XXXXXXXXXXX|
|WHITELIST_CHANNEL|String|ホワイトリスト編集のチャンネル|XXXXXXXXXXX|

### WHITELIST
|key name|type|about|default|
|----|----|----|----|
|MULTIPLE|Bool|Discordアカウントが複数で登録できるようにする。|false|
#### JOIN_GAME
|key name|type|about|default|
|----|----|----|----|
|BOOL|Bool|Discordアカウントが複数で登録できるようにする。|false|
|TYPE|String|テキストを送信することでコマンドを実行するかDiscordのコマンド機能を使うか TXTまたはCMDを指定|TXT|
|CHANNEL|String|join gameコマンドを実行できるチャンネルを指定する|XXXXXXXXXXX|
#### CMD
|key name|type|about|default|
|----|----|----|----|
|BOOL|Bool|ホワイトリスト追加コマンドを実行できるようにするかしないか|false|
|SHOW_ALL|Bool|コマンド実行時に実行者以外に見えるようにするか|false|
|ALLOW_MC_ONLY|Bool|Discordアカウントのリンクをせずホワイトリストに追加するだけを可能にする|true|
|CHANNEL|String|コマンドを実行できるチャンネルを指定する|XXXXXXXXXXX|

### SERVER_STATUS
|key name|type|about|default|
|----|----|----|----|
|SYNC_BOOL|Bool|VCでのサーバーステータスチェックをできるようにするか|false|
|SYNC_CHANNEL|String|ステータスを表示すVCのチャンネル|XXXXXXXXXXX|
|ENABLE_TEXT|String|サーバー起動時のテキスト|"🟢サーバーが起動中"|
|DISABLE_TEXT|String|サーバー停止時のテキスト|"🔴サーバーが停止中"|

### SERVER_INFO
|key name|type|about|default|
|----|----|----|----|
|SERVER_NOTICE_BOOL|Bool|サーバー起動・停止メッセージをDiscordに送信するか|true|
|STAT_MESS|String|サーバー起動メッセージ|":green_circle: サーバーが起動しました"|
|CLOSE_MESS|String|サーバー停止メッセージ|":red_circle: サーバーが停止しました"|

### USER_INFO
|key name|type|about|default|
|----|----|----|----|
|USER_NOTICE_BOOL|Bool|サーバー入退室をDiscordに送信するか|true|
#### JOIN_NOTICE & LEAVE_NOTICE
|key name|type|about|default|
|----|----|----|----|
|TYPE|String|マイクラサーバー入室・退室時のメッセージタイプ embedかtext|"embed"|
|MESSAGE|String|入退室時のメッセージ|false|
|COLOR|String(HEX)|embedの横の色|入室 37ab58 / 退室 #c44949|

### MESSAGE_TYPE
|key name|type|about|default|
|----|----|----|----|
|D2M|String|DiscordからMinecraftに送信する際の形式※|"<&{UserName}>&{message}"|
|M2D|String|MinecraftからDiscordに送信する際の形式※|"<&{UserName}>&{message}"|
|DISCORD_ONLY|String|MESSAGE_SYNC_CHANNELに送信されたメッセージでDiscordにのみ送信する時の頭文字|"¥"|
|MINECRAFT_ONLY|String|Minecraftのチャットに送信されたメッセージでDiscordに送信しないメッセージの頭文字|"*"|


### CMD
|key name|type|about|default|
|----|----|----|----|
|CMD_NOTICE_BOOL|Bool|コマンドをDiscordに送信するか否か|true|
|CMD_NOTICE_MESS|String|コマンド送信時に送るメッセージ|"がコマンドを利用した! : "|
  
### ORE
#### GET_NOTICE
|key name|type|about|default|
|----|----|----|----|
|SEND_MINECRAFT|Bool|Minecraftに鉱石採取通知を送信するか。|true|
|SEND_DISCORD|Bool|Discordに鉱石採取通知を送信するか。|true|

##### NOTICE_LIST
|key name|type|about|default|
|----|----|----|----|
|name|String|Minecraftの鉱石名||
|sendText|String|送信メッセージの内容(&{UserName}を入れることでユーザー名を入れれます)||

### BED
|key name|type|about|default|
|----|----|----|----|
|SLEEP_NOTICE|Bool|ベッド就寝通知を送信するか。|true|

## ※ &{UserName}と&{message}について  
&{UserName}にはユーザー名、&{message}にはメッセージ内容が入ります。  
例

|文字列|出力|
|----|----|
|<&{UserName}>&{message}|<hoge>huga|
|[&{UserName}]&{message}|[hoge]huga|
|&{UserName} : &{message}|hoge : huga|