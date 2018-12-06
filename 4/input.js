regExps = {
"exercise_1": /Internet|Words|Or/,
"exercise_2": /08{2}[1-7]{7}/,
"exercise_3": /[^0-1]+/,
"exercise_4": /^[A-Za-z][\w.]{2,14}$/,
"exercise_5": /^(?!1501|2000)[0-9]{3,4}$/,
"exercise_6": /class=(\"\D+\"|\'\D+\')/
};
cssSelectors = {
"exercise_1": " item > java.class1 ",
"exercise_2": " different > java.diffClass ",
"exercise_3": " java > tag.class1.class2",
"exercise_4": " css > item:nth-child(3) ",
"exercise_5": " tag > java:nth-child(2).class1 ",
"exercise_6": " item.class2 > item.class1, item.class1 > item.class2 ",
"exercise_7": " different#diffId2 > java:last-child ",
"exercise_8": " css > #someId "
};
