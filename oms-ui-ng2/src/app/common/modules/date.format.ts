export class DateFormat {

    public getDateFromNanoSecond(nanoSecond) {
        return new Date(nanoSecond).getMonth() + 1 + "-" + new Date(nanoSecond).getDate() + "-" + new Date(nanoSecond).getFullYear();
    }
}