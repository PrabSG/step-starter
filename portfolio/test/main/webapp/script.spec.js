describe('Slide Pictures', () => {
  
  let items = []
  
  beforeEach(() => {
    items = [0, 1, 2, 3];
  })

  it('returns valid index', () => {
      expect(circularIndex(3, items))
        .toBe(3);
  });

  it('loops to starting indices given large index', () => {
      expect(circularIndex(4, items))
        .toBe(0);
      expect(circularIndex(5, items))
        .toBe(1);
  });

  it('loops to ending indices given small index', () => {
      expect(circularIndex(-1, items))
        .toBe(items.length - 1);
      expect(circularIndex(-2, items))
        .toBe(items.length - 2);
  });

  it('handles looping multiple times with very large index', () => {
      expect(circularIndex(items.length * 2 + 2, items))
        .toBe(2);
  });

  it('handles looping multiple times with very large negative index', () => {
      expect(circularIndex(- (items.length * 2 + 2), items))
        .toBe(items.length - 2);
  });
});

