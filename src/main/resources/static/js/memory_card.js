let firstSelectedCardId = 0;
let secondSelectedCardId = 0;
let cards = document.querySelectorAll(".card");
let newGameButton = document.querySelector('#new_game');
let pair = false;
let foundPairsIds = new Set();

const cardBackImgUrl = "url(../img/card_back.jpg)";

(function init() {

    for (let i = 0; i < cards.length; ++i) {

        cards[i].innerHTML = "-";
        cards[i].style.backgroundImage = cardBackImgUrl;
        cards[i].style.color = "white";
        cards[i].addEventListener("click", turn);
    }
    
    newGameButton.addEventListener("click", function() {
		window.location.href = window.location.href;
	});

})()

function turn(e) {

    if (pair == false) {

        // Turn card
        if (e.target.innerHTML === "-") {

            e.target.style.backgroundImage = "";
            e.target.style.color = "black";
            e.target.innerHTML = e.target.getAttribute("data-value");
        }
    
        // Strore selected second card id
        if (firstSelectedCardId != 0 && secondSelectedCardId == 0 && e.target.id != firstSelectedCardId) {
            secondSelectedCardId = e.target.id;
            pair = true;
        }
        // Strore selected first card id
        if (firstSelectedCardId == 0 && e.target.id != secondSelectedCardId)
            firstSelectedCardId = e.target.id;
    }

    // Face-up research
    if (firstSelectedCardId != 0 && secondSelectedCardId != 0) {

        let firstSelectedCard = document.getElementById(firstSelectedCardId);
        let secondSelectedCard = document.getElementById(secondSelectedCardId);

        // If selected cards are not a pair
        if (firstSelectedCard.getAttribute("name") !== secondSelectedCard.getAttribute("name"))
            setTimeout(turnBack, 1000);
        else
        { // If selected cards are a pair
            foundPairsIds.add(firstSelectedCardId);
            foundPairsIds.add(secondSelectedCardId);
            pair = false;
        }

        firstSelectedCardId = 0;
        secondSelectedCardId = 0;

        // Game Over inquiry
        if (foundPairsIds.size == cards.length)
        {
        	document.getElementById("succes").style.display = 'block';
        	newGameButton.style.display = 'block';
        }
    }
}

// Turn back cards which are not exist in foundPairsIds set
function turnBack() {
    for (let i = 0; i < cards.length; ++i) {

        if (!foundPairsIds.has(cards[i].id)) 
        {
            cards[i].innerHTML = "-";
            cards[i].style.backgroundImage = cardBackImgUrl;
            cards[i].style.color = "white";
        }
    }
    pair = false;
}