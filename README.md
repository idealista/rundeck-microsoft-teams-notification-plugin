# Rundeck Microsoft Teams Notification Plugin

## Goal
Allows to set up notification for *Microsoft Teams* chats for a channel, via Workflow URL.
To use it you will have to obtain *workflow* for your Microsoft Teams channel first and set it up.
You may find it in Microsoft Teams Channel user interfaces by using
Flows via: **Workflows -> Create flow from templates -> Post a workflow when a webhook request is received**

## Testing simple notification message
Sending of simple notification message can be tested manualy with terminal curl program:   
```
curl -v -k -X POST -H "Content-Type: application/json" -d "{\"type\": \"message\",\"attachments\": [{\"contentType\": \"application/vnd.microsoft.card.adaptive\",\"contentUrl\": null,\"content\": {\"type\": \"AdaptiveCard\",\"version\": \"1.4\",\"body\": [{\"type\": \"TextBlock\",\"text\": \"Notification from Rundeck\"}]}}]}" WEBHOOK_URL
```

## Manual Instalation from terminal
1. Copy plugin to rundeck directory ($RDECK_BASE) as appropriate user (rundeck)
and restart rundeck server as appropriate user (admin)
```
$RDECK_BASE = /var/lib/Rundeck
sudo -u rundeck bash
cp ./rundeck-microsoft-teams-notification-plugin/src/MicrosoftTeamsNotification.groovy $RDECK_BASE/libext
exit
sudo /etc/init.d/rundeckd restart
```
2. If you not familiar with groovy and want to play with it to debug plugin code
you may install groovy with sdkman
```
curl -s "https://get.sdkman.io" | bash
# source it or reopen termial then:
sdkman install groovy
```

## Exemplary content of variable 'execution'

```
[
  id:324897,
  href:http://rundeck.domain-name.com/project/project-name/execution/follow/324897,
  status:running,
  user:user-name,
  dateStarted:2017-05-16 11:12:18.674,
  dateStartedUnixtime:1494925938674,
  dateStartedW3c:2017-05-16T09:12:18Z,
  description:Provides the export for the hekate dashboard,
  argstring:null,
  project:project-name,
  failedNodeListString:null,
  failedNodeList:null,
  succeededNodeListString:null,
  succeededNodeList:null,
  loglevel:INFO,
  abortedby:null,
  job:
  [
    id:00d49dce-65ce-4b75-b5b6-d9fe79aaf237,
    href:http://rundeck.domain-name.com/project/project-name/job/show/00d49dce-65ce-4b75-b5b6-d9fe79aaf237,
    name:job-name,
    group:exports,
    project:project-name,
    description:Provides the export for the hekate dashboard,
    averageDuration:9965
  ],
  context:[
    globals:[:],
    job:
    [
      loglevel:INFO,
      wasRetry:false,
      url:http://rundeck.domain-name.com/project/project-name/execution/follow/324897,
      id:00d49dce-65ce-4b75-b5b6-d9fe79aaf237,
      project:project-name,
      user.email:user@company.com,
      username:user-name,
      retryAttempt:0,
      user.name:user-name,
      name:job-name,
      serverUUID:null,
      group:exports,
      serverUrl:http://rundeck.domain-name.com/,
      execid:324897
    ],
    option:
    [:
    ]
  ]
]
```
