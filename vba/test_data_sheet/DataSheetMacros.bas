Attribute VB_Name = "NewMacros"

Public Type header
    test_case_id As String
    test_case_title As String
    tester As String
    sw_version As String
    procedure As String
    step As String
    requirements() As String
End Type

Public excel_data(99) As header

Function get_row_count(rng As Range) As Long
    Application.Volatile
    Dim row_index As String
    row_index = rng.Parent.cells(Rows.count, rng.Column).End(xlUp).Address(rowabsolute:=True)
    cell_address_array = Split(row_index, "$")
    get_row_count = cell_address_array(2)

End Function

Sub read_data()

    Dim XLSheet As Object
    Dim data_range As Range
    
    Set XLSheet = worksheets("Data")
    Set data_range = XLSheet.Range("data_info")
    
    Dim rows_in_data As Long
    rows_in_data = get_row_count(data_range)
                            
    For r = 2 To rows_in_data
        For c = 1 To 7
            If c = 1 Then
                excel_data(r).test_case_id = XLSheet.cells(r, c)
            ElseIf c = 2 Then
                excel_data(r).test_case_title = XLSheet.cells(r, c)
            ElseIf c = 3 Then
                excel_data(r).tester = XLSheet.cells(r, c)
            ElseIf c = 4 Then
                excel_data(r).sw_version = XLSheet.cells(r, c)
            ElseIf c = 5 Then
                excel_data(r).procedure = XLSheet.cells(r, c)
            ElseIf c = 6 Then
                excel_data(r).step = XLSheet.cells(r, c)
            ElseIf c = 7 Then
                excel_data(r).requirements = Split(XLSheet.cells(r, c), ",")
            End If
        Next c
    Next r
    
    Set XLSheet = Nothing
    
End Sub
Sub split_row(cells As Integer, doc As Object)
'
' test Macro
' Macro recorded 8/22/2004 by cbh
'
    With doc.activedocument.Tables
        With .Selection
            .cells.Split NumRows:=1, NumColumns:=cells, MergeBeforeSplit:=True
        End With
    End With
    
End Sub

Sub add_row_w_cols(col_count As Integer, doc As Object)
Attribute add_row_w_cols.VB_Description = "Macro recorded 8/22/2004 by cbh"
Attribute add_row_w_cols.VB_ProcData.VB_Invoke_Func = "Normal.NewMacros.add_row_w_cols"
'
' add_row_w_cols Macro
' Macro recorded 8/22/2004 by cbh
'
    With doc.activedocument.Tables
        With .Selection
            .InsertRowsBelow 1
        End With
        Call split_row(col_count, doc)
    End With
    
End Sub

Sub tab_over(count As Integer, doc As Object)
'
' tab_over Macro
' Macro recorded 8/22/2004 by cbh
'
    For c = 1 To count
        With doc
            With .Selection
                .MoveRight Unit:=wdCell
            End With
        End With
    Next c
End Sub

Sub create_table(doc As Object)
'
' test Macro
' Macro recorded 8/22/2004 by cbh
'

    Set myRange = doc.activedocument.Range(Start:=doc.activedocument.Range.End - 1, _
                                            End:=doc.activedocument.Range.End)
                                            
    doc.activedocument.Tables.Add Range:=myRange, NumRows:=1, NumColumns:=1
    
    MsgBox doc.activedocument.Range.End
    
'    doc.activedocument.Range.Move Unit:=wdCharacter, count:=1

    
End Sub

Function get_len(list() As String)

    Dim non_blank As Integer, count As Integer
    non_blank = 0
    count = 0

    On Error GoTo handler
    
    Do
        If Not IsEmpty(list(count)) Then
            non_blank = non_blank + 1
        End If
        count = count + 1
    Loop
    
    get_len = non_blank
    
handler:
        get_len = non_blank
        Exit Function
End Function

Sub add_header(doc As Object)
'
' test Macro
' Macro recorded 8/23/2004 by cbh
'
    With doc
        With .Selection
            .typeText "Header text"
        End With
    End With
    
End Sub
Sub setup_page_margins(doc As Object)
'
' setup_page_margins Macro
' Macro recorded 8/23/2004 by cbh
'
    With doc.activedocument
        With .PageSetup
            .LineNumbering.Active = False
            .Orientation = wdOrientPortrait
            .TopMargin = Application.InchesToPoints(1.5)
            .BottomMargin = Application.InchesToPoints(1)
            .LeftMargin = Application.InchesToPoints(1)
            .RightMargin = Application.InchesToPoints(1)
            .Gutter = Application.InchesToPoints(0)
            .HeaderDistance = Application.InchesToPoints(0.5)
            .FooterDistance = Application.InchesToPoints(0.5)
            .PaperSize = xlPaperLetter
            .FirstPageTray = wdPrinterDefaultBin
            .OtherPagesTray = wdPrinterDefaultBin
            .OddAndEvenPagesHeaderFooter = False
            .DifferentFirstPageHeaderFooter = False
            .VerticalAlignment = wdAlignVerticalTop
            .SuppressEndnotes = False
            .MirrorMargins = False
            .TwoPagesOnOne = False
            .GutterPos = wdGutterPosLeft
        End With
    End With
End Sub

Sub write_data()

    Dim myarray() As header
    Dim index As Integer
    read_data
    myarray = excel_data
    
    Dim wordapp As Object
    Set wordapp = CreateObject("Word.Application")
    wordapp.documents.Add
    wordapp.Visible = True
    
    Call setup_page_margins(wordapp)
    Call add_header(wordapp)
    
    For e = 0 To 99
        If Not myarray(e).test_case_id = "" Then
        
            Call create_table(wordapp)
            
            With wordapp
                With .Selection
                    .typeText "Test Case ID: "
                    .typeText myarray(e).test_case_id
                End With
            
                Call add_row_w_cols(1, wordapp)
                
                With .Selection
                    .typeText "Test Case Title: "
                    .typeText myarray(e).test_case_title
                End With
                        
                Call add_row_w_cols(2, wordapp)
                
                With .Selection
                    .typeText "Test Operator(s): " & myarray(e).tester
                End With
                
                Call tab_over(1, wordapp)
                
                With .Selection
                    .typeText "SQE Witness: "
                End With
                
                Call add_row_w_cols(2, wordapp)
                Call tab_over(2, wordapp)
                
                With .Selection
                    .typeText "IV & V Witness: "
                End With
                
                Call add_row_w_cols(2, wordapp)
                Call tab_over(2, wordapp)
                
                With .Selection
                    .typeText "Safety Witness: "
                End With
                
                Call add_row_w_cols(3, wordapp)
                
                With .Selection
                    .typeText "Date: "
                End With
                
                Call tab_over(1, wordapp)
                
                With .Selection
                    .typeText "Start Time: "
                End With
                
                Call tab_over(1, wordapp)
                
                With .Selection
                    .typeText "Stop Time: "
                End With
                
                Call add_row_w_cols(2, wordapp)
                
                With .Selection
                    .typeText "SW Version: " & myarray(e).sw_version
                End With
            
                Call tab_over(1, wordapp)
                
                With .Selection
                    .typeText "PASS: " & "                      " & "FAIL:"
                End With
                
                Call add_row_w_cols(4, wordapp)
                
                With .Selection
                    .typeText "Procedure ID"
                    .ParagraphFormat
                    .Alignment = wdAlignParagraphCenter
                End With
                
                Call tab_over(1, wordapp)
                
                With .Selection
                    .typeText "Step"
                    .ParagraphFormat
                    .Alignment = wdAlignParagraphCenter
                End With
                
                Call tab_over(1, wordapp)
                
                With .Selection
                    .typeText "Requirement"
                    .ParagraphFormat
                    .Alignment = wdAlignParagraphCenter
                End With
                
                Call tab_over(1, wordapp)
                
                With .Selection
                    .typeText "Comments"
                    .ParagraphFormat
                    .Alignment = wdAlignParagraphCenter
                End With
                
                Dim num_req As Integer
                num_req = get_len(myarray(e).requirements)
                Dim procedure_written As Boolean
                procedure_written = False
                Dim req_written As Boolean
                req_written = False
                
                For blank_row = 1 To (16 - num_req)
                    Call add_row_w_cols(4, wordapp)
                
                    If Not procedure_written Then
                    
                        With .Selection
                            .ParagraphFormat
                            .LineSpacingRule = wdLineSpaceDouble
                            .typeText myarray(e).procedure
                        End With
                        
                        procedure_written = True
                        
                        Call tab_over(1, wordapp)
                    
                        With .Selection
                            .typeText myarray(e).step
                        End With
                    
                        Call tab_over(1, wordapp)
                    
                    End If
                
                    If Not req_written Then
                        For req = 0 To (num_req - 1)
                        
                            With .Selection
                                .typeText myarray(e).requirements(req)
                                .ParagraphFormat
                                .LineSpacingRule = wdLineSpaceDouble
                            End With
                        
                            Call add_row_w_cols(4, wordapp)
                            Call tab_over(3, wordapp)
                        Next req
                        req_written = True
                    End If
                    
                Next blank_row
            
                With .Selection
                    .EndKey Unit:=wdStory
                    .InsertBreak Type:=wdPageBreak
                End With
            
                .activedocument.SaveAs Filename:=ThisWorkbook.Path & "\" & myarray(e).test_case_id & ".doc"
            
            End With
        End If
    Next e
    
    wordapp.Quit
    Set wordapp = Nothing
    
    
    
End Sub
