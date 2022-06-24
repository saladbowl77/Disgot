# config.yml 設定方法
## デフォルト設定
```yml
TOKEN: XXXXXXXXXXX.XXXXXX.XXXXXXXXXXX
SERVER_ID: XXXXXXXXXXXXXXXXXX
BOT_STATUS: "hogehoge"
MESSAGE_SYNC_CHANNEL: XXXXXXXXXXX
WHITELIST_CHANNEL: XXXXXXXXXXX
SERVER_STATUS:
  SYNC_BOOL: "false"
  SYNC_CHANNEL: XXXXXXXXXXX
  ENABLE_TEXT: "🟢サーバーが起動中"
  DISABLE_TEXT: "🔴サーバーが停止中"
SERVER_INFO:
  SERVER_NOTICE_BOOL : "true"
  STAT_MESS : ":green_circle: サーバーが起動しました"
  CLOSE_MESS : ":red_circle: サーバーが停止しました"
USER_INFO:
  USER_NOTICE_BOOL : "true"
  JOIN_MESS : "がゲームに参加しました!いらっしゃい!"
  LEAVE_MESS : "がゲームを離れました。バイバイ"
MESSAGE_TYPE:
  D2M : "<&{UserName}>&{message}"
  M2D : "<&{UserName}>&{message}"
  DISCORD_ONLY : "¥"
  MINECRAFT_ONLY : "*"
CMD:
  CMD_NOTICE_BOOL : "true"
  CMD_NOTICE_MESS : "がコマンドを利用した! : "
ORE:
  GET_NOTICE:
    SEND_MINECRAFT: "true"
    SEND_DISCORD": "true"
BED:
  SLEEP_NOTICE: "true"
```

## 各種項目内容
|key name|type|about|default|
|----|----|----|----|
|TOKEN|String|Discord Token|XXXXXXXXXXX.XXXXXX.XXXXXXXXXXX|
|BOT_STATUS|String|Botのステータス|hogehoge|
|MESSAGE_SYNC_CHANNEL|String|メッセージを送信するチャンネル|XXXXXXXXXXX|
|WHITELIST_CHANNEL|String|ホワイトリスト編集のチャンネル|XXXXXXXXXXX|

#### SERVER_STATUS
|key name|type|about|default|
|----|----|----|----|
|SYNC_BOOL|String(bool)|VCでのサーバーステータスチェックをできるようにするか|"false"|
|SYNC_CHANNEL|String|ステータスを表示すVCのチャンネル|XXXXXXXXXXX|
|ENABLE_TEXT|String|サーバー起動時のテキスト|"🟢サーバーが起動中"|
|DISABLE_TEXT|String|サーバー停止時のテキスト|"🔴サーバーが停止中"|

#### SERVER_INFO
|key name|type|about|default|
|----|----|----|----|
|SERVER_NOTICE_BOOL|String(bool)|サーバー起動・停止メッセージをDiscordに送信するか|"true"|
|STAT_MESS|String|サーバー起動メッセージ|":green_circle: サーバーが起動しました"|
|CLOSE_MESS|String|サーバー停止メッセージ|":red_circle: サーバーが停止しました"|

#### USER_INFO
|key name|type|about|default|
|----|----|----|----|
|USER_NOTICE_BOOL|String(bool)|サーバー入退室をDiscordに送信するか|"true"|
|JOIN_MESS|String|サーバー起動メッセージ|"がゲームに参加しました!いらっしゃい"|
|LEAVE_MESS|String|サーバー停止メッセージ|"がゲームを離れました。バイバイ"|

#### MESSAGE_TYPE
|key name|type|about|default|
|----|----|----|----|
|D2M|String|DiscordからMinecraftに送信する際の形式※|"<&{UserName}>&{message}"|
|M2D|String|MinecraftからDiscordに送信する際の形式※|"<&{UserName}>&{message}"|
|DISCORD_ONLY|String|MESSAGE_SYNC_CHANNELに送信されたメッセージでDiscordにのみ送信する時の頭文字|"¥"|
|MINECRAFT_ONLY|String|Minecraftのチャットに送信されたメッセージでDiscordに送信しないメッセージの頭文字|"*"|

### ※ &{UserName}と&{message}について  
&{UserName}にはユーザー名、&{message}にはメッセージ内容が入ります。  
例

|文字列|出力|
|----|----|
|<&{UserName}>&{message}|<hoge>huga|
|[&{UserName}]&{message}|[hoge]huga|
|&{UserName} : &{message}|hoge : huga|


#### CMD
|key name|type|about|default|
|----|----|----|----|
|CMD_NOTICE_BOOL|String(bool)|コマンドをDiscordに送信するか否か|"true"|
|CMD_NOTICE_MESS|String|コマンド送信時に送るメッセージ|"がコマンドを利用した! : "|
  
#### ORE
##### GET_NOTICE
|key name|type|about|default|
|----|----|----|----|
|SEND_MINECRAFT|String(bool)|Minecraftに鉱石採取通知を送信するか。|"true"|
|SEND_DISCORD|String(bool)|Discordに鉱石採取通知を送信するか。|"true"|
  
#### BED
|key name|type|about|default|
|----|----|----|----|
|SLEEP_NOTICE|String(bool)|ベッド就寝通知を送信するか。|"true"|
