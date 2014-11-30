package hiddenmessages.serverside;

import net.minecraft.init.Blocks;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.Random;

/**
 * User: The Grey Ghost
 * Date: 30/11/2014
 */
public class CreateBlocks
{
  public void placeBlocks(World world, Vec3 eyePosition, Vec3 lookDirection, double distanceToCentre, double cloudRadius)
  {
    Vec3 normalisedLook = Vec3.createVectorHelper(lookDirection.xCoord, lookDirection.yCoord, lookDirection.zCoord);
    normalisedLook.normalize();
    Vec3 offsetToCentre = Vec3.createVectorHelper(normalisedLook.xCoord * distanceToCentre,
            normalisedLook.yCoord * distanceToCentre,
            normalisedLook.zCoord * distanceToCentre);

    Vec3 cloudCentrePos = eyePosition.addVector(offsetToCentre.xCoord, offsetToCentre.yCoord, offsetToCentre.zCoord);

    String [] target = {".......",
                        ".#####.",
                        "...#...",
                        "...#...",
                        "...#...",
                        ".#####.",
                        "......."};

    StringBuffer [] working = new StringBuffer[target.length];

    for (int i = 0; i < target.length; ++i) {
      working[i] = new StringBuffer(target[i]);
    }

    int columns = target[0].length();
    int rows = target.length;

    // the target must have an odd number of columns and an odd number of rows otherwise the centre doesn't line up properly
    assert (columns % 2 != 0) && (rows % 2 != 0);

    long sCentre = (columns - 1) / 2;
    long tCentre = (rows - 1) / 2;
    Random random = new Random();

    // The imaging plane is the plane onto which the hidden image is visible; when the player is looking perpendicular to the
    //   plane and is the correct distance away, the blocks in the cloud will form an image on the plane
    // The plane is normal to the look vector and is located at imagePlaneP0
    // The image on the plane is defined in two dimensions by [s,t] where s has no y component and t is perpendicular to s (positive y)
    // It can be calculated from the look vector by
    // 1) find the cross product of the look vector and the positive y axis.  This is the s axis.
    // 2) find the cross product of the look vector and the s axis.  This is the t axis.
    double imagePlaneDistance = distanceToCentre / 2.0;
    Vec3 imagePlaneP0 = Vec3.createVectorHelper(eyePosition.xCoord + normalisedLook.xCoord * imagePlaneDistance,
            eyePosition.yCoord + normalisedLook.yCoord * imagePlaneDistance,
            eyePosition.zCoord + normalisedLook.zCoord * imagePlaneDistance);
    Vec3 imagePlaneP0fromEye = eyePosition.subtract(imagePlaneP0);
    Vec3 yaxis = Vec3.createVectorHelper(0, 1, 0);
    Vec3 saxis = normalisedLook.crossProduct(yaxis);
    saxis.normalize();
    Vec3 taxis = saxis.crossProduct(normalisedLook);
    taxis.normalize();

    int RANDOMLOCATIONCOUNT = 100;
    for (int i = 0; i < RANDOMLOCATIONCOUNT; ++i) {
      double x = (2.0 * random.nextDouble() - 1.0) * cloudRadius;
      double y = (2.0 * random.nextDouble() - 1.0) * cloudRadius;
      double z = (2.0 * random.nextDouble() - 1.0) * cloudRadius;
      if (x*x + y*y + z*z <= cloudRadius*cloudRadius) {
        int cloudXint = (int)Math.round(x + cloudCentrePos.xCoord - 0.5);
        int cloudYint = (int)Math.round(y + cloudCentrePos.yCoord - 0.5);
        int cloudZint = (int)Math.round(z + cloudCentrePos.zCoord - 0.5);

        double cloudX = cloudXint + 0.5;
        double cloudY = cloudYint + 0.5;
        double cloudZ = cloudZint + 0.5;

        // Algorithm is:
        // We have chosen a random cloudPoint within a radius around the centre point
        // The vector from the eyePosition to the random point is the cloudPoint vector.
        // We need to project that point onto the imaging plane, where the imaging plane is
        // perpendicular to the look vector and is located at imagePlaneDistance distance from the eyePosition
        // The point in the imaging plane corresponds to the intersection of the cloudPoint vector with the imaging plane
        // Formula for intersection of a vector with a plane:
        // plane is defined as (p - p0) dot n = 0
        // where p = point, p0 = any point in the plane, n is the normal vector to the plane
        // cloudPoint vector is p = d.v + v0
        // where p = point, d = distance along vector, v = vector direction, v0 = vector origin
        // solving for d -> d = ((p0 - v0) dot n )/ (v dot n)
        // This then gives the point in the imaging plane pi, which needs to be converted to the s, t coordinates in the imaging plane
        // a vector is constructed from pi to the imaging plan origin, then the projection of this vector onto the left edge of the imaging plane gives the vertical (t) coordinate
        //  likewise the projection onto the bottom edge gives the horizontal (s) coordinate

        Vec3 cloudPointVector = Vec3.createVectorHelper(cloudX - eyePosition.xCoord,
                                                        cloudY - eyePosition.yCoord,
                                                        cloudZ - eyePosition.zCoord);
        double numerator = imagePlaneP0fromEye.dotProduct(normalisedLook);
        double denominator = cloudPointVector.dotProduct(normalisedLook);
        double d = numerator / denominator;
        Vec3 imagingPlanePoint = eyePosition.addVector(d * cloudPointVector.xCoord, d * cloudPointVector.yCoord, d * cloudPointVector.zCoord);

        // construct projection of the point onto the s axis and t axis using dot product.  The units of s correspond to one block 'width' at the imaging plane
        // the origin is P0, where the look vector intersects the imaging plane, so s and t will be centred around zero
        Vec3 temp = imagePlaneP0.subtract(imagingPlanePoint);
        double s = temp.dotProduct(saxis);
        double t = temp.dotProduct(taxis);
        long slong = Math.round(s) + sCentre;
        long tlong = Math.round(t) + tCentre;
        if (slong >= 0 && slong < columns && tlong >= 0 && tlong < rows) {
          int sint = (int)slong;
          int tint = (int)tlong;
          if (working[tint].charAt(sint) == '#') {
            working[tint].replace(sint, sint, "o");
            world.setBlock(cloudXint, cloudYint, cloudZint, Blocks.stone);
          }
        }
      }
    }
  }




}
