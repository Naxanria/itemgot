package com.naxanria.itemgot.util;

public class MathUtil
{
  public static int clamp(int v, int min, int max)
  {
    return  (v > max) ? max : (v < min) ? min : v;
  }
}
