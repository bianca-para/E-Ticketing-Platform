LISTA DE OBIECTE
Event – reprezinta un eveniment organizat
Venue – locatia in care are loc un eveniment
Category – genul evenimentului (MUSIC, TECH etc.)
Ticket – biletul emis pentru un eveniment
User (abstract) – clasa de baza pentru toate tipurile de utilizatori
Participant – utilizator care cumpara bilete
Moderator – utilizator care modereaza evenimente/report-uri 
Admin – utilizator cu drepturi de configurare a platformei (poate gestiona moderatori, seta parametri de sistem si vede un audit log al actiunilor sale)


LISTA DE ACTIUNI:
registerUser(User u)
findUserById(String userId)
listUsers()
createEvent(Event e)
updateEvent(Event e)
listEvents()
searchEventsByCategory(Category c)
registerAttendee(String eventId, String userId)
listAttendees(String eventId)
buyTicket(String eventId, String userId)
listTicketsForUser(String userId)
cancelTicket(String ticketId)
