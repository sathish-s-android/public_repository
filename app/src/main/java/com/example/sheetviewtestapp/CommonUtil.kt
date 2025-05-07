package com.example.sheetviewtestapp

import android.content.Context
import com.zoho.aksheetview.ui.base.model.logic.entities.commonModel.ActionMenuItem
import com.zoho.aksheetview.ui.base.model.logic.entities.commonModel.Padding
import com.zoho.aksheetview.ui.base.model.logic.entities.commonModel.ResizeActionMenuItem
import com.zoho.aksheetview.ui.base.model.logic.entities.coreModel.sheetViewCell.SheetViewCell
import com.zoho.aksheetview.ui.base.model.logic.entities.coreModel.sheetViewCell.StatusCell
import com.zoho.aksheetview.ui.base.model.logic.entities.coreModel.sheetViewColumn.SheetViewCellProperties
import com.zoho.aksheetview.ui.base.model.logic.entities.coreModel.sheetViewColumn.SheetViewColumn
import com.zoho.aksheetview.ui.base.model.logic.entities.coreModel.sheetViewColumn.SheetViewColumnTypes
import com.zoho.aksheetview.ui.base.model.logic.entities.coreModel.sheetViewRow.SheetViewRow
import com.zoho.aksheetview.ui.base.model.logic.entities.coreModel.sheetViewRow.SheetViewRowHeader
import com.zoho.aksheetview.ui.base.model.logic.entities.coreModel.sheetViewRow.SubItemsState
import com.zoho.aksheetview.ui.base.model.logic.entities.customizerModel.SVColor
import com.zoho.aksheetview.ui.base.model.logic.entities.customizerModel.SVIcon
import com.zoho.aksheetview.ui.base.model.logic.entities.customizerModel.SVString
import com.zoho.aksheetview.ui.base.model.logic.entities.customizerModel.SVText

class CommonUtil {

    companion object {

        private const val SAMPLE_TITLE_COLUMN_ID = "10001"

        private fun getStringFromResource(resId: Int, context: Context): String {
            return context.getString(resId)
        }

        fun getSampleSheetViewRows(context: Context): List<SheetViewRow> {
            val tempSheetViewRowList = mutableListOf<SheetViewRow>()

            tempSheetViewRowList.add(
                SheetViewRowHeader(
                    rowHeaderId = "H001",
                    rowHeaderName = SVText(text = SVString.StringText("Sample header 1")),
                    subItemsState = SubItemsState.HasSubItems(isExpanded = true)
                )
            )

            tempSheetViewRowList.add(
                SheetViewRow(
                    rowId = "L001",
                    rowName = SVString.StringText("Sample item 1"),
                    level = 1,
                    parentRowId = "H001",
                    columnIdVsCellValueMap = hashMapOf(
                        "10001" to SheetViewCell(
                            value = SVText(text = SVString.StringText("Sample item 1"))
                        ), "10002" to StatusCell(
                            value = SVText(
                                text = SVString.StringText(
                                    getStringFromResource(
                                        R.string.in_progress_status, context
                                    )
                                )
                            ), bgColor = SVColor.SVColorRes(
                                colorResId = R.color.in_progress_status_color
                            )
                        ), "10003" to SheetViewCell(
                            value = SVText(text = SVString.StringText("1738302476793"))
                        )
                    )
                )
            )

            tempSheetViewRowList.add(
                SheetViewRow(
                    rowId = "L002",
                    rowName = SVString.StringText("Sample item 2"),
                    level = 1,
                    parentRowId = "H001",
                    columnIdVsCellValueMap = hashMapOf(
                        "10001" to SheetViewCell(
                            value = SVText(text = SVString.StringText("Sample item 2"))
                        ), "10002" to StatusCell(
                            value = SVText(
                                text = SVString.StringText(
                                    getStringFromResource(
                                        R.string.to_be_tested_status, context
                                    )
                                )
                            ), bgColor = SVColor.SVColorRes(
                                colorResId = R.color.to_be_tested_status_color
                            )
                        ), "10003" to SheetViewCell(
                            value = SVText(text = SVString.StringText("1732802476793"))
                        )
                    )
                )
            )

            tempSheetViewRowList.add(
                SheetViewRow(
                    rowId = "L003",
                    rowName = SVString.StringText("Sample item 3"),
                    level = 1,
                    parentRowId = "H001",
                    columnIdVsCellValueMap = hashMapOf(
                        "10001" to SheetViewCell(
                            value = SVText(text = SVString.StringText("Sample item 3"))
                        ), "10002" to StatusCell(
                            value = SVText(
                                text = SVString.StringText(
                                    getStringFromResource(
                                        R.string.open_status, context
                                    )
                                )
                            ), bgColor = SVColor.SVColorRes(
                                colorResId = R.color.open_status_color
                            )
                        ), "10003" to SheetViewCell(
                            value = SVText(text = SVString.StringText("1628302476793"))
                        )
                    )
                )
            )

            tempSheetViewRowList.add(
                SheetViewRow(
                    rowId = "L004",
                    rowName = SVString.StringText("Sample item 4"),
                    level = 1,
                    parentRowId = "H001",
                    subItemsState = SubItemsState.HasSubItems(isExpanded = false),
                    hasMultiSelectPermission = false,
                    columnIdVsCellValueMap = hashMapOf(
                        "10001" to SheetViewCell(
                            value = SVText(text = SVString.StringText("Sample item 4"))
                        ), "10002" to StatusCell(
                            value = SVText(
                                text = SVString.StringText(
                                    getStringFromResource(
                                        R.string.cancelled_status, context
                                    )
                                )
                            ), bgColor = SVColor.SVColorRes(
                                colorResId = R.color.cancelled_status_color
                            )
                        ), "10003" to SheetViewCell(
                            value = SVText(text = SVString.StringText("1583968476793"))
                        )
                    )
                )
            )

            tempSheetViewRowList.add(
                SheetViewRow(
                    rowId = "SLI004-1",
                    rowName = SVString.StringText("Sample sub item 1"),
                    level = 2,
                    parentRowId = "L004",
                    columnIdVsCellValueMap = hashMapOf(
                        "10001" to SheetViewCell(
                            value = SVText(text = SVString.StringText("Sample sub item 1"))
                        ), "10002" to StatusCell(
                            value = SVText(
                                text = SVString.StringText(
                                    getStringFromResource(
                                        R.string.reopen_status, context
                                    )
                                )
                            ), bgColor = SVColor.SVColorRes(
                                colorResId = R.color.reopen_status_color
                            )
                        ), "10003" to SheetViewCell(
                            value = SVText(text = SVString.StringText("1403991486793"))
                        )
                    )
                )
            )

            tempSheetViewRowList.add(
                SheetViewRow(
                    rowId = "L005",
                    rowName = SVString.StringText("Sample item 5"),
                    level = 1,
                    parentRowId = "H001",
                    columnIdVsCellValueMap = hashMapOf(
                        "10001" to SheetViewCell(
                            value = SVText(text = SVString.StringText("Sample item 5"))
                        ), "10002" to StatusCell(
                            value = SVText(
                                text = SVString.StringText(
                                    getStringFromResource(
                                        R.string.closed_status, context
                                    )
                                )
                            ), bgColor = SVColor.SVColorRes(
                                colorResId = R.color.closed_status_color
                            )
                        ), "10003" to SheetViewCell(
                            value = SVText(text = SVString.StringText("1808302476793"))
                        )
                    )
                )
            )

            return tempSheetViewRowList
        }

        fun getSampleSheetViewColumns(context: Context): List<SheetViewColumn> {

            val popupMenuFieldItemOptionsList = listOf(
                ActionMenuItem(
                    actionItemIdentifier = "401", actionItemActionName = SVText(
                        text = SVString.StringText(
                            getStringFromResource(
                                R.string.column_action2,
                                context
                            )
                        ),
                        textColor = SVColor.SVColorRes(
                            colorResId = R.color.black
                        )
                    ), actionItemIcon = SVIcon.SVIconRes(
                        iconResId = R.drawable.lock_24,
                        iconDescription = SVString.StringText("add task"),
                        iconColor = SVColor.SVColorRes(
                            colorResId = R.color.black
                        )
                    )
                ), ResizeActionMenuItem(), ActionMenuItem(
                    actionItemIdentifier = "402", actionItemActionName = SVText(
                        text = SVString.StringText(
                            getStringFromResource(
                                R.string.column_action3,
                                context
                            )
                        ),
                        textColor = SVColor.SVColorRes(
                            colorResId = R.color.black
                        )
                    ), actionItemIcon = SVIcon.SVIconRes(
                        iconResId = R.drawable.baseline_update_24,
                        iconDescription = SVString.StringText("add task"),
                        iconColor = SVColor.SVColorRes(
                            colorResId = R.color.black
                        )
                    )
                ), ActionMenuItem(
                    actionItemIdentifier = "403", actionItemActionName = SVText(
                        text = SVString.StringText(
                            getStringFromResource(
                                R.string.column_action4,
                                context
                            )
                        ),
                        textColor = SVColor.SVColorRes(
                            colorResId = R.color.black
                        )
                    ), actionItemIcon = SVIcon.SVIconRes(
                        iconResId = R.drawable.baseline_speaker_group_24,
                        iconDescription = SVString.StringText("add task"),
                        iconColor = SVColor.SVColorRes(
                            colorResId = R.color.black
                        )
                    )
                ), ActionMenuItem(
                    actionItemIdentifier = "404", actionItemActionName = SVText(
                        text = SVString.StringText(
                            getStringFromResource(
                                R.string.column_action5,
                                context
                            )
                        ),
                        textColor = SVColor.SVColorRes(
                            colorResId = R.color.black
                        )
                    ), actionItemIcon = SVIcon.SVIconRes(
                        iconResId = R.drawable.baseline_sports_rugby_24,
                        iconDescription = SVString.StringText("add task"),
                        iconColor = SVColor.SVColorRes(
                            colorResId = R.color.black
                        )
                    )
                )
            )

            val tempHeaderList = mutableListOf<SheetViewColumn>()
            tempHeaderList.add(
                SheetViewColumn(
                    columnID = SAMPLE_TITLE_COLUMN_ID,
                    columnTitle = SVText(
                        text = SVString.StringText(getStringFromResource(R.string.title, context)),
                        textColor = SVColor.SVColorRes(
                            colorResId = R.color.sheet_view_column_name_text_color
                        )
                    ),
                    columnType = SheetViewColumnTypes.SimpleTextField,
                    isVisible = true,
                    shouldShowActionMenu = false,
                    sheetViewCellProperties = SheetViewCellProperties(
                        padding = Padding(
                            paddingStart = 8, paddingEnd = 15
                        )
                    )
                )
            )

            tempHeaderList.add(
                SheetViewColumn(
                    columnID = "10002",
                    columnTitle = SVText(
                        text = SVString.StringText(getStringFromResource(R.string.status, context)),
                        textColor = SVColor.SVColorRes(
                            colorResId = R.color.sheet_view_column_name_text_color
                        )
                    ),
                    columnType = SheetViewColumnTypes.StatusField,
                    isVisible = true,
                    moreActionMenuItems = popupMenuFieldItemOptionsList,
                    sheetViewCellProperties = SheetViewCellProperties(
                        padding = Padding(
                            paddingEnd = 8, paddingStart = 8, paddingTop = 8, paddingBottom = 8
                        )
                    )
                )
            )

            tempHeaderList.add(
                SheetViewColumn(
                    columnID = "10003",
                    columnTitle = SVText(
                        text = SVString.StringText(
                            getStringFromResource(
                                R.string.date_icon_description,
                                context
                            )
                        ),
                        textColor = SVColor.SVColorRes(
                            colorResId = R.color.sheet_view_column_name_text_color
                        )
                    ),
                    columnType = SheetViewColumnTypes.DateAndTimeField(isNeedTime = false),
                    isVisible = true,
                    moreActionMenuItems = popupMenuFieldItemOptionsList,
                    sheetViewCellProperties = SheetViewCellProperties(
                        padding = Padding(
                            paddingEnd = 12, paddingStart = 12
                        )
                    )
                )
            )

            return tempHeaderList
        }

    }

}