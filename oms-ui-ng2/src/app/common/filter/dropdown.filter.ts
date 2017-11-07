
export class DropDownDataFilter {
    public static convertDataIntoDropdown(array, propId, propLabel) {
        if (propId === 'creativeId') {
            let newArr = array.map(function (val, index) {
                return { 'id': val[propId], 'name': val[propLabel] + "(" + val['height1'] + " x " + val['width1'] + ")" };
            });
            return newArr;
        } else if (propId == 'rateTypeId') {
            let newArr = array.map(function (val, index) {
                return { 'id': val[propId], 'name': val[propLabel] + " ( " + val['description'] + " )" };
            });
            return newArr;
        } else {
            let newArr = array.map(function (val, index) {
                return { 'id': val[propId], 'name': val[propLabel] };
            });
            return newArr;
        }
    }


    public static convertDataTargetTypeValuesDropdown(array, propId, propLabel, isSelected) {
        let newArr = array.map(function (val, index) {
            return { 'id': val[propId], 'label': val[propLabel], 'isSelected': isSelected };
        });
        return newArr;

    }
}