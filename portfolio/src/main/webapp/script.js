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

"use strict";

/**
 * Fetches comments stored in backend servlet.
 */
function getComments() {
  fetch('/data')
    .then(response => response.json())
    .then(comments => {
      const section = document.getElementById('comment-section');
      
      for (const comment of comments) {
        const post = document.createElement('p');
        post.innerText = comment;

        section.appendChild(post);
      }
    });
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
 * Create gallery elements and set images.
 */
function setGalleryImages() {
  const slideContainer = document.getElementsByClassName('slides-container')[0];
  const nextBtn = document.getElementsByClassName('next-slide')[0];
  
  // If we are on Home page gallery will not be defined.
  if (typeof slideContainer === 'undefined') {
    return;
  }

  for (const pic of picData) {
    const slidePic = document.createElement('div');
    slidePic.className = 'slide-pic';

    const counter = document.createElement('div');
    counter.className = 'slide-counter';

    const img = document.createElement('img');
    img.src = pic.imgSrc;

    slidePic.appendChild(counter);
    slidePic.appendChild(img);

    slideContainer.insertBefore(slidePic, nextBtn);
  }
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

  const newIndex = circularIndex(n, slides);
  
  for (const slide of slides) {
    slide.style.display = 'none';
  }

  slides[newIndex].style.display = 'block';
  
  caption.innerText = picData[newIndex].caption;
  slides[newIndex].getElementsByClassName('slide-counter')[0].innerText =
     (newIndex + 1).toString() + '/' + slides.length.toString();

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
  const map = new google.maps.Map(
    document.getElementById('map'),
    {zoom: picData[currIndex].mapZoom, center: picData[currIndex].location}
  )

  const marker = new google.maps.Marker(
    {position: picData[currIndex].location, map: map}
  );
}

const picData = [
  {
    imgSrc: './images/gallery_1.jpeg',
    caption: 'This was taken outside King\'s College Chapel in Cambridge ' +
      'last summer. I decided to take a scenic route and accidentally timed ' +
      'this photo with a cyclist going past.',
    location: {lat: 52.204, lng: 0.117},
    mapZoom: 12
  },
  {
    imgSrc: './images/gallery_2.jpeg',
    caption: 'IC A vs Birmingham B in the NHSF National Kabaddi Tournament ' +
      'earlier this year. I joined Kabaddi at Imperial this year and I am ' +
      'loving it!',
    location: {lat: 52.546, lng: -2.052},
    mapZoom: 12
  },
  {
    imgSrc: './images/gallery_3.jpeg',
    caption: 'A photo of the Marina Bay Sands Hotel in Singapore during one ' +
      'of its light shows, taken from across the bay.',
    location: {lat: 1.285, lng: 103.854},
    mapZoom: 12
  },
  {
    imgSrc: './images/gallery_4.jpeg',
    caption: 'A firebreather in the desert. Taken during a desert safari ' +
      'and dinner excursion when visiting Dubai.',
    location: {lat: 25.179, lng: 55.299},
    mapZoom: 10
  },
  {
    imgSrc: './images/gallery_5.jpeg',
    caption: 'A close-up of a flower in the Flower Dome of the Gardens By ' +
      'the Bay in Singapore.',
    location: {lat: 1.284, lng: 103.865},
    mapZoom: 12
  }
];

setGalleryImages();
let currIndex = showSlidePicture(0);