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
                                type: "TextBlock",
                                size: "Medium",
                                weight: "Bolder",
                                text: "The job ${execution.project} >> ${execution.job.name} #${execution.id} has started",
                                wrap: true
                            ],
                            [
                                type: "ColumnSet",
                                columns: [
                                    [
                                        type: "Column",
                                        items: [
                                            [
                                                type: "Image",
                                                url: "https://raw.githubusercontent.com/rundeck/rundeck/main/rundeckapp/grails-app/assets/images/logos/rundeck2-icon-32.png",
                                                altText: "Rundeck Logo",
                                                size: "Medium"
                                            ]
                                        ],
                                        width: "auto"
                                    ],
                                    [
                                      type: "Column",
                                      items: [
                                          [
                                              type: "TextBlock",
                                              weight: "Bolder",
                                              text: "Rundeck Job Notification",
                                              wrap: true
                                          ],
                                          [
                                              type: "TextBlock",
                                              spacing: "None",
                                              text: "${execution.dateStarted}",
                                              isSubtle: true,
                                              wrap: true
                                          ]
                                      ],
                                      width: "stretch"
                                    ]
                                ]
                            ],
                            [
                                type: "RichTextBlock",
                                inlines: [
                                    [
                                        type: "TextRun",
                                        text: "[${type}]",
                                        weight: "Bolder",
                                        size: "Medium",
                                        color: "Accent"
                                    ]
                                ]
                            ],
                            [
                                type: "FactSet",
                                facts: [
                                    [
                                        title: "Job project",
                                        value: "${execution.project}"
                                    ],
                                    [
                                        title: "Job name",
                                        value: "${execution.job.name}"
                                    ],
                                    [
                                        title: "Job id",
                                        value: "${execution.id}"
                                    ],
                                    [
                                        title: "Job status",
                                        value: "${execution.status}"
                                    ],
                                    [
                                        title: "Started at",
                                        value: "${execution.dateStarted}"
                                    ]
                                ]
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
                                type: "TextBlock",
                                size: "Medium",
                                weight: "Bolder",
                                text: "The job ${execution.project} >> ${execution.job.name} #${execution.id} completed successfully",
                                wrap: true
                            ],
                            [
                                type: "ColumnSet",
                                columns: [
                                    [
                                        type: "Column",
                                        items: [
                                            [
                                                type: "Image",
                                                url: "https://raw.githubusercontent.com/rundeck/rundeck/main/rundeckapp/grails-app/assets/images/logos/rundeck2-icon-32.png",
                                                altText: "Rundeck Logo",
                                                size: "Small"
                                            ]
                                        ],
                                        width: "auto"
                                    ],
                                    [
                                      type: "Column",
                                      items: [
                                          [
                                              type: "TextBlock",
                                              weight: "Bolder",
                                              text: "Rundeck Job Notification",
                                              wrap: true
                                          ],
                                          [
                                              type: "TextBlock",
                                              spacing: "None",
                                              text: "${execution.dateStarted}",
                                              isSubtle: true,
                                              wrap: true
                                          ]
                                      ],
                                      width: "stretch"
                                    ]
                                ]
                            ],
                            [
                                type: "RichTextBlock",
                                inlines: [
                                    [
                                        type: "TextRun",
                                        text: "[${type}]",
                                        weight: "Bolder",
                                        size: "Medium",
                                        color: "Good"
                                    ]
                                ]
                            ],
                            [
                                type: "FactSet",
                                facts: [
                                    [
                                        title: "Job project",
                                        value: "${execution.project}"
                                    ],
                                    [
                                        title: "Job name",
                                        value: "${execution.job.name}"
                                    ],
                                    [
                                        title: "Job id",
                                        value: "${execution.id}"
                                    ],
                                    [
                                        title: "Job status",
                                        value: "${execution.status}"
                                    ],
                                    [
                                        title: "Started at",
                                        value: "${execution.dateStarted}"
                                    ],
                                    [
                                        title: "Ended at",
                                        value: "${execution.dateEnded}"
                                    ]
                                ]
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
                                type: "TextBlock",
                                size: "Medium",
                                weight: "Bolder",
                                text: "The job ${execution.project} >> ${execution.job.name} #${execution.id} failed",
                                wrap: true
                            ],
                            [
                                type: "ColumnSet",
                                columns: [
                                    [
                                        type: "Column",
                                        items: [
                                            [
                                                type: "Image",
                                                url: "https://raw.githubusercontent.com/rundeck/rundeck/main/rundeckapp/grails-app/assets/images/logos/rundeck2-icon-32.png",
                                                altText: "Rundeck Logo",
                                                size: "Small"
                                            ]
                                        ],
                                        width: "auto"
                                    ],
                                    [
                                      type: "Column",
                                      items: [
                                          [
                                              type: "TextBlock",
                                              weight: "Bolder",
                                              text: "Rundeck Job Notification",
                                              wrap: true
                                          ],
                                          [
                                              type: "TextBlock",
                                              spacing: "None",
                                              text: "${execution.dateStarted}",
                                              isSubtle: true,
                                              wrap: true
                                          ]
                                      ],
                                      width: "stretch"
                                    ]
                                ]
                            ],
                            [
                                type: "RichTextBlock",
                                inlines: [
                                    [
                                        type: "TextRun",
                                        text: "[${type}]",
                                        weight: "Bolder",
                                        size: "Medium",
                                        color: "Attention"
                                    ]
                                ]
                            ],
                            [
                                type: "FactSet",
                                facts: [
                                    [
                                        title: "Job project",
                                        value: "${execution.project}"
                                    ],
                                    [
                                        title: "Job name",
                                        value: "${execution.job.name}"
                                    ],
                                    [
                                        title: "Job id",
                                        value: "${execution.id}"
                                    ],
                                    [
                                        title: "Job status",
                                        value: "${execution.status}"
                                    ],
                                    [
                                        title: "Started at",
                                        value: "${execution.dateStarted}"
                                    ],
                                    [
                                        title: "Ended at",
                                        value: "${execution.dateEnded}"
                                    ]
                                ]
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