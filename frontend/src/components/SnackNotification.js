import {Snackbar,Alert} from "@mui/material"

const SnackNotification = ({message, severity, open, openHandler}) => {
    const handleClose = (reason) => {
        if (reason === 'clickaway') {
            return
        }
        openHandler(false)
    }
    return (
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
            <Alert severity={severity} onClose={handleClose}>{message}</Alert>
        </Snackbar>
    )
}

export default SnackNotification
