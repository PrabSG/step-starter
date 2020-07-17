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
  const modal = document.getElementById(modalId);
  const modalImg = document.getElementById(modalImgId);
  const caption = document.getElementById(captionId);

  modal.style.display = 'block';
  modalImg.src = imgSrc;
  caption.innerText = captionText;

  const closeBtn = document.getElementById(closeId);
  closeBtn.onclick = function() {
    modal.style.display = 'none';
  };
}

/**
 * Advance or Reverse the slide currently showing.
 */
function advanceSlides(n) {
  currIndex += n;
  currIndex = showSlidePicture(currIndex);
  initMap();
};

/**
 * Show the image from the gallery at the given position.
 */
function showSlidePicture(n) {
  const slides = document.getElementsByClassName('slide-pic');
  const caption = document.getElementById('slide-caption');

  // If not on pics.html document wil not contain any slide elements.
  if (slides.length === 0) {
    return n;
  }

  let newIndex = circularIndex(n, slides);
  
  for (let slide of slides) {
    slide.style.display = "none";
  }

  slides[newIndex].style.display = 'block';
  slides[newIndex].getElementsByClassName('slide-counter')[0].innerText =
     (newIndex + 1).toString() + '/' + slides.length.toString();
  caption.innerText = picCaptions[newIndex];

  return newIndex;
};

/**
 * Return correct index for a circular array.
 */
function circularIndex(n, items) {
  while (n < 0) {
    n += items.length;
  }

  return n % items.length;
}

/**
 * Initialise map for currently displayed image.
 */
function initMap() {
  let map = new google.maps.Map(
    document.getElementById('map'),
    {zoom: picZoom[currIndex], center: picLocations[currIndex]}
  )

  let marker = new google.maps.Marker(
    {position: picLocations[currIndex], map: map}
  );
}

const picCaptions = [
  `This was taken outside King's College Chapel in Cambridge last summer. I ` +
    'decided to take a scenic route and accidentally timed this photo with ' +
    'a cyclist going past.',
  'IC A vs Birmingham B in the NHSF National Kabaddi Tournament earlier ' +
    'this year. I joined Kabaddi at Imperial this year and I am loving it!',
  'A photo of the Marina Bay Sands Hotel in Singapore during one of its '+
    'light shows, taken from across the bay.',
  'A firebreather in the desert. Taken during a desert safari and dinner ' +
    'excursion when visiting Dubai.',
  'A close-up of a flower in the Flower Dome of the Gardens By the Bay in ' +
    'Singapore.'];
const picLocations = [
  {lat: 52.204, lng: 0.117},
  {lat: 52.546, lng: -2.052},
  {lat: 1.285, lng: 103.854},
  {lat: 25.179, lng: 55.299},
  {lat: 1.284, lng: 103.865}
];
const picZoom = [15, 12, 15, 12, 15]

let currIndex = showSlidePicture(0);