package org.hodgson.development.util;

public interface E5Actions
{
	/**
	 * The Send Action.
	 */
	public static final short SEND = 0;

	/**
	 * Sign-on Actions.
	 */
	public static final short CLEAR = 1;
	public static final short STEAL = 2;
	public static final short STEAL_CLEAR = 3;

	/**
	 * Edit Actions.
	 */
	public static final short EDIT_VIEW = 501;
	public static final short EDIT_AMEND = 502;
	public static final short EDIT_INSERT = 503;
	public static final short EDIT_COPY = 504;
	public static final short EDIT_DISABLE = 505;
	public static final short EDIT_ENABLE = 506;
	public static final short EDIT_PURGE = 507;
	public static final short EDIT_UNLOCK = 508;

	/**
	 * Fetch Actions.
	 */
	public static final short FETCH = 510;
	public static final short FETCH_VIEW = 511;

	/**
	 * Search Actions.
	 */
	public static final short EXTENDED_SELECTION = 920;
	public static final short SEARCH_OR = 921;
	public static final short SEARCH_AND = 922;
	public static final short SELECTION_SAVE = 923;
	public static final short FAVOURITE_ENQUIRIES = 924;
	public static final short SELECTION_DELETE = 925;
	public static final short SELECTION_DEFAULT = 926;
	public static final short SELECTION_DISPLAY = 927;

	/**
	 * Session Actions.
	 */
	public static final short SESSION_CLOSE = 928;
	public static final short SESSION_SUSPEND = 930;
	public static final short SESSION_NEXT = 931;
	public static final short SESSION_PREVIOUS = 932;
	public static final short SESSION_CREATE = 933;
	public static final short SESSION_TRACE = 934;
	public static final short SESSION_MENU = 937;
	public static final short SESSION_STACK = 938;
	public static final short SESSION_LOG = 939;

	/**
	 * General Actions.
	 */
	public static final short UPDATE = 940;
	public static final short ACCEPT_WARNINGS = 941; // Accept Warnings and Update
	public static final short EXIT = 942;
	public static final short REFRESH = 943;
	public static final short RESTORE = 945;
	public static final short INTERRUPT = 946;
	public static final short SELECTION_MORE = 947;
	public static final short LINE_WARNINGS = 948; // Accept Warnings; No Update
	public static final short SORT = 949;
	public static final short PROMPT = 950;
	public static final short QUEUE_SUBMIT = 953;
	public static final short DIARY_WORKLIST = 954;
	public static final short ASCENDING_SORT = 955;
	public static final short DESCENDING_SORT = 956;

	/**
	 * Navigation Actions.
	 */
	public static final short FORWARD = 960;
	public static final short BACKWARD = 961;
	public static final short LEFT = 962;
	public static final short RIGHT = 963;
	public static final short NEXT_FORMAT = 964;
	public static final short SELECTION_WINDOW = 970;

	/**
	 * Help Actions.
	 */
	public static final short HELP = 980;
	public static final short HELP_USING = 981;
	public static final short HELP_FIELD_LEVEL = 982;
	public static final short HELP_RELATED = 983;
	public static final short HELP_INDEX = 984;
	public static final short HELP_TUTORIAL = 985;
	public static final short HELP_SCREEN_LEVEL = 986;
	public static final short HELP_LEDGER_LEVEL = 987;
	public static final short HELP_RESERVE_1 = 988;
	public static final short HELP_RESERVE_2 = 989;

	/**
	 * The Cancel Action.
	 */
	public static final short CANCEL = 998;

	// Interface Defined Actions. (Start at 1000)
	// public static final short _C_A_C_H_E = 1000;
	public static final short PRINT = 1001;
	public static final short PRINT_METHOD_PRINTER = 1002;
	public static final short PRINT_METHOD_BITMAP = 1003;
	public static final short PRINT_METHOD_OLD = 1004;

	public static final short DUMMY_SCROLL = 1009;
	public static final short FIT_WIDTH = 1010;
	public static final short GRID_LINES = 1011;
	public static final short GRID_COLORS = 1012;
	public static final short COLUMN_FILTER = 1013;
	public static final short THEME = 1014;
	public static final short VERTICAL_FORM = 1015;
	public static final short COLUMN_FILL = 1016;
	public static final short GRID_COPY = 1017;
	public static final short DOWNLOAD = 1018;
	public static final short DUMMY_SORT = 1019;

	public static final short AUTO_TAB = 1020;
	public static final short AUTO_COMPLETE = 1021;

	public static final short ZOOM_NORMAL = 1030; // Zoom 1:1/1:1
	public static final short ZOOM_640x480 = 1031; // Zoom 640x480
	public static final short ZOOM_800x600 = 1032; // Zoom 800x600
	public static final short ZOOM_1024x768 = 1033; // Zoom 1024x768
	public static final short ZOOM_1280x1024 = 1034; // Zoom 1280x1024

	public static final short ABOUT = 1040;
}