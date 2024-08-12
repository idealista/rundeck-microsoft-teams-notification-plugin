import com.dtolabs.rundeck.plugins.notification.NotificationPlugin
import groovy.json.JsonOutput

rundeckPlugin(NotificationPlugin) {
    title = "Microsoft Teams notification Plugin"
    description = "Allows to set up notification for Microsoft Teams chats for a channel, via Workflow URL. To use it you will have to obtain workflow for your channel first and set it up."

    configuration {
        webhook_url title: "Webhook URL", required: true, type: "String", description: "You may find it in Microsoft Teams Channel user interfaces by using Flows via: Workflows -> Create flow from templates -> Post a workflow when a webhook request is received"
    }

    onstart {
        type = "START"
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
                                        color: "Accent"
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
        process = ['bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${adaptive_card_payload}' '${configuration.webhook_url}'"].execute().text

        return true
    }

    onsuccess {
        type = "SUCCESS"
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
                                        color: "Good"
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
                                text: "Job Status: ${execution.status}",
                                wrap: true
                            ],
                            [
                                type: "TextBlock",
                                text: "Started At: ${execution.dateStarted}",
                                wrap: true
                            ],
                            [
                                type: "TextBlock",
                                text: "Ended At: ${execution.dateEnded}",
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
        process = ['bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${adaptive_card_payload}' '${configuration.webhook_url}'"].execute().text

        return true
    }

    onfailure {
        type = "FAILURE"
        adaptive_card_payload = JsonOutput.toJson([
            type: "message",
            attachments: [
                [
                    contentType: "application/vnd.microsoft.card.adaptive",
                    content: [
                        type: "AdaptiveCard",
                        version: "1.2",
                        body: [
                            [
                                type: "RichTextBlock",
                                inlines: [
                                    [
                                        type: "TextRun",
                                        text: "[${type}] Rundeck Job Notification",
                                        weight: "Bolder",
                                        size: "Medium",
                                        color: "Attention"
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
                                text: "Job Status: ${execution.status}",
                                wrap: true
                            ],
                            [
                                type: "TextBlock",
                                text: "Started At: ${execution.dateStarted}",
                                wrap: true
                            ],
                            [
                                type: "TextBlock",
                                text: "Ended At: ${execution.dateEnded}",
                                wrap: true
                            ],
                            [
                                type: "ActionSet",
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
                ]
            ]
        ])
        process = ['bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${adaptive_card_payload}' '${configuration.webhook_url}'"].execute().text

        return true
    }
}