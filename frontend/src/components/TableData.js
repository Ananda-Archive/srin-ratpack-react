import Button from "@mui/material/Button"
import { DataGrid } from "@mui/x-data-grid"

const TableData = ({header, datas, dataHandler, dialogHandler}) => {

    // Variable
    const action = [
        {
            field:"action", headerName:"Action",
            renderCell: (params) => {
                return <Button sx={{color:"red"}} onClick= {(event) => {
                    dataHandler(params.row)
                    dialogHandler(3)
                    event.stopPropagation() // Stop double click on button and row at the same time
                }}>Delete</Button>
            }
        }
    ]

    return (
        <div style={{ height:800, width:'100%' }}>
            <DataGrid
                columns={[...header,...action]}
                rows={datas}
                pageSize={13}
                rowsPerPageOptions={[13]}
                disableSelectionOnClick
                disableColumnMenu
                onCellClick={(params) => {
                    dataHandler(params.row)
                    dialogHandler(2)
                }}
            />
        </div>
    )
}

export default TableData
