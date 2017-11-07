import { IMultiSelectSettings } from "angular-2-dropdown-multiselect";
import { IMultiSelectTexts } from "angular-2-dropdown-multiselect";

export class DropDownSetting {

    public static singleSelectSettings: IMultiSelectSettings = {
        selectionLimit: 1,
        autoUnselect: true,
        dynamicTitleMaxItems: 1,
        displayAllSelectedText: false,
        closeOnSelect: true,
        checkedStyle: 'glyphicon',
    };
    public static multiSelectSelectSettings: IMultiSelectSettings = {
        showCheckAll: true,
        showUncheckAll: true,
        checkedStyle: 'glyphicon'

    };

    public static textSetting: IMultiSelectTexts = {
        checkAll: 'Select all',
        uncheckAll: 'Unselect all',
        checked: 'item selected',
        checkedPlural: 'items selected',
        searchPlaceholder: 'Find',
        defaultTitle: 'Select',
        allSelected: 'All selected',
    };
    constructor() {

    }


}