
var myApp = angular.module("myApp",[]);
myApp.controller("signupcontroller",function ($scope,$http) {
var wordToGuess = ['mehdi','azoui','khawla','soukli'];
$scope.incorrectLetters = [];
$scope.correctLetters = [];
$scope.guessesNumber = 6;
$scope.randomWordHtmlHidden = '';
$scope.randomWordHtml = '';
$scope.randomword = function () {
    var index = Math.floor(Math.random()*wordToGuess.length);
    $scope.randomWordHtml = wordToGuess[index];
    var temp = '';
    for(var i=0;i<wordToGuess[index].length;i++){
        temp+='*';
    }

    $scope.randomWordHtmlHidden = temp;
    $scope.guessesNumber = 6;
    return wordToGuess[index];
}

$scope.guessrandomword = function () {
    var letter = $scope.input.letter;
    var temp = '';
    var currentGuessNumber = $scope.guessesNumber;
    var temphiddenword = $scope.randomWordHtmlHidden;
    var trouve = false;
    for(var i=0;i<($scope.randomWordHtml).length;i++){
        if(letter == ($scope.randomWordHtml).charAt(i) ){
             temp += letter;
             trouve = true;
             $scope.correctLetters[$scope.correctLetters.length] = letter;
        }
       
        else  if('*' != temphiddenword.charAt(i) ){
            temp += temphiddenword.charAt(i);
        }
        
        else
        temp+='*';
    }
    $scope.randomWordHtmlHidden = temp;
    $scope.input.letter = '';
    if(trouve==false)
    {
        currentGuessNumber--;
        $scope.guessesNumber = currentGuessNumber;
        $scope.incorrectLetters[$scope.incorrectLetters.length] = letter;
    }

}


});