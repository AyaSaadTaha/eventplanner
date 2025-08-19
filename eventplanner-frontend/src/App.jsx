import { BrowserRouter, Routes, Route } from 'react-router-dom';
import EventsPage from './pages/EventsPage';
import ParticipantsPage from './pages/ParticipantsPage';
import { CssBaseline, AppBar, Toolbar, Typography, Container } from '@mui/material';
import { Link } from 'react-router-dom';
// import './App.css'

export default function App() {
    return (
        <BrowserRouter>
            <CssBaseline />
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Event Planner
                    </Typography>
                    <Link to="/" style={{ color: 'white', marginRight: 20, textDecoration: 'none' }}>
                        Events
                    </Link>
                    <Link to="/participants" style={{ color: 'white', textDecoration: 'none' }}>
                        Participants
                    </Link>
                </Toolbar>
            </AppBar>
            <Container maxWidth="lg" sx={{ mt: 4 }}>
                <Routes>
                    <Route path="/" element={<EventsPage />} />
                    <Route path="/participants" element={<ParticipantsPage />} />
                </Routes>
            </Container>
        </BrowserRouter>
    );
}