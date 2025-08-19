import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, IconButton } from '@mui/material';
import { Edit, Delete } from '@mui/icons-material';
import ParticipantActions from "./ParticipantActions.jsx";

export default function ParticipantList({ participants, onEdit, onDelete }) {
    return (
        <TableContainer component={Paper} sx={{ mt: 3 }}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>First Name</TableCell>
                        <TableCell>Last Name</TableCell>
                        <TableCell>Email</TableCell>
                        <TableCell>Actions</TableCell>
                        <TableCell></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {participants.map((participant) => (
                        <TableRow key={participant.id}>
                            <TableCell>{participant.firstName}</TableCell>
                            <TableCell>{participant.lastName}</TableCell>
                            <TableCell>{participant.email}</TableCell>
                            <TableCell>
                                <IconButton onClick={() => onEdit(participant)}>
                                    <Edit color="primary" />
                                </IconButton>
                                <IconButton onClick={() => onDelete(participant.id)}>
                                    <Delete color="error" />
                                </IconButton>
                            </TableCell>
                            <TableCell>
                                <ParticipantActions participantId={participant.id} />
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
}