const weekends = ["Saturday", "Sunday"];

export function CheckWeekends(date) {
    const day = date.getDay();
    if (weekends.includes(day)) {
        return true;
    }
    return false;
}
