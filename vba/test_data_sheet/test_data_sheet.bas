Attribute VB_Name = "NewMacros"
Sub make_uc()
Attribute make_uc.VB_Description = "Macro recorded 6/15/2004 by default"
Attribute make_uc.VB_ProcData.VB_Invoke_Func = "Normal.NewMacros.make_uc"
'
' make_uc Macro
' Macro recorded 6/15/2004 by default
'
    Selection.Range.Case = wdUpperCase
End Sub
Sub add_row()
Attribute add_row.VB_Description = "Macro recorded 8/20/2004 by default"
Attribute add_row.VB_ProcData.VB_Invoke_Func = "Normal.NewMacros.add_row"
'
' add_row Macro
' Macro recorded 8/20/2004 by default
'
    ActiveDocument.Tables.Add Range:=Selection.Range, NumRows:=1, NumColumns:= _
        1, DefaultTableBehavior:=wdWord9TableBehavior, AutoFitBehavior:= _
        wdAutoFitFixed
End Sub
Sub add_cell()
Attribute add_cell.VB_Description = "Macro recorded 8/20/2004 by default"
Attribute add_cell.VB_ProcData.VB_Invoke_Func = "Normal.NewMacros.add_cell"
'
' add_cell Macro
' Macro recorded 8/20/2004 by default
'
    Selection.InsertCells ShiftCells:=wdInsertCellsShiftRight
End Sub
Sub move_right()
Attribute move_right.VB_Description = "Macro recorded 8/20/2004 by default"
Attribute move_right.VB_ProcData.VB_Invoke_Func = "Normal.NewMacros.move_right"
'
' move_right Macro
' Macro recorded 8/20/2004 by default
'
    Selection.MoveRight Unit:=wdCharacter, Count:=1
End Sub
Sub tab_right()
Attribute tab_right.VB_Description = "Macro recorded 8/20/2004 by default"
Attribute tab_right.VB_ProcData.VB_Invoke_Func = "Normal.NewMacros.tab_right"
'
' tab_right Macro
' Macro recorded 8/20/2004 by default
'
    Selection.MoveRight Unit:=wdCell
End Sub
