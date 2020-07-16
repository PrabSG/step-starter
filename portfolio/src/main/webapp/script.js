// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Hello world!', '¡Hola Mundo!', '你好，世界！', 'Bonjour le monde!'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

/**
 * Opens a modal with the given image and caption.
 */
function openSimpleImageModal(modalId, modalImgId, captionId, closeId, imgSrc, captionText) {  
  var modal = document.getElementById(modalId);
  var modalImg = document.getElementById(modalImgId);
  var caption = document.getElementById(captionId);

  modal.style.display = "block";
  modalImg.src = imgSrc;
  caption.innerHTML = captionText;

  var closeBtn = document.getElementById(closeId);
  closeBtn.onclick = function() {
    modal.style.display = "none";
  };
}

/**
 * Advance or Reverse the slide currently showing.
 */
function advanceSlides(n) {
  currIndex += n;
  currIndex = showSlidePicture(currIndex);
};

/**
 * Show the image from the gallery at the given position.
 */
function showSlidePicture(n) {
  const slides = document.getElementsByClassName("slide-pic");
  const caption = document.getElementById("slide-caption");

  let newIndex = n;
  if (n >= slides.length) {
    newIndex = 0;
  }
  if (n < 0) {
    newIndex = slides.length - 1;
  }
  
  for (let i = 0; i < slides.length; i++) {
    slides[i].style.display = "none";
  }

  console.log(slides);
  console.log(newIndex);
  slides[newIndex].style.display = "block";
  slides[newIndex].getElementsByClassName("slide-counter")[0].innerText =
     (newIndex + 1).toString() + "/" + slides.length.toString();
  caption.innerText = picCaptions[newIndex];

  return newIndex;
};

let picCaptions = ["This is caption 1", "This is caption 2", "This is caption 3", "This is caption 4", "This is caption 5"]
let currIndex = showSlidePicture(0);