//import com.dtolabs.rundeck.plugins.notification.NotificationPlugin
import groovy.json.JsonOutput

def sendMessage(type, color, configuration, execution) {
  //with no args, there is a "configuration" and an "execution" variable in the context
  //sendMessage(type, color, configuration, execution)
  adaptive_card_payload = JsonOutput.toJson([
    type: "message",
    attachments: [
      [
        contentType: "application/vnd.microsoft.card.adaptive",
        contentUrl: null,
        content: [
          type: "AdaptiveCard",
          version: "1.4",
          body: [
            [
              type: "RichTextBlock",
              inlines: [
                [
                  type: "TextRun",
                  text: "[${type}] Rundeck Job Notification",
                  weight: "Bolder",
                  size: "Medium",
                  color: "${color}"
                ]
              ]
            ],
            [
              type: "TextBlock",
              text: "Job project: ${execution.project}",
              wrap: true
            ],
            [
              type: "TextBlock",
              text: "Job name: ${execution.job.name}",
              wrap: true
            ],
            [
              type: "TextBlock",
              text: "Job id: ${execution.id}",
              wrap: true
            ],
            [
              type: "TextBlock",
              text: "Job status: ${execution.status}",
              wrap: true
            ],
            [
              type: "TextBlock",
              text: "Started at: ${execution.dateStarted}",
              wrap: true
            ]
          ],
          actions: [
            [
              type: "Action.OpenUrl",
              title: "Seed job execution",
              url: "${execution.href}"
            ]
          ]
        ]
      ]
    ]
  ])

  return adaptive_card_payload
}

configuration = [
  'workflow_url': '<URL HERE>'
]

execution = [
  'id': 'xyz',
  'project': 'projectx',
  'status': 'succes',
  'dateEnded': '2017-05-05'
]

type = "START"
color = "Accent" // "Accent" or "Good" or "Warning" or "Attention"

adaptive_card_payload = sendMessage(type, color, configuration, execution)
process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${adaptive_card_payload}' '${configuration.workflow_url}'" ].execute().text
print process
