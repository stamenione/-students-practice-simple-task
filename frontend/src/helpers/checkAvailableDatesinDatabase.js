export function CheckAvailableDatesinDatabase(date, response) {
    const exists = response.find((el) => el === date);
    if (exists) return false;
    return true;
}
