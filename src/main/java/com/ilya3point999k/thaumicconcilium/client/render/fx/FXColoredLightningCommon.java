package com.ilya3point999k.thaumicconcilium.client.render.fx;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.client.fx.WRVector3;

import java.util.*;

public class FXColoredLightningCommon {
    ArrayList segments;
    WRVector3 start;
    WRVector3 end;
    HashMap splitparents;
    public float multiplier;
    public float length;
    public int numsegments0;
    public int increment;
    public int type;
    public boolean nonLethal;
    private int numsplits;
    private boolean finalized;
    private Random rand;
    public long seed;
    public int particleAge;
    public int particleMaxAge;
    private World world;
    public static final float speed = 3.0F;
    public static final int fadetime = 20;

    public FXColoredLightningCommon(World world, WRVector3 jammervec, WRVector3 targetvec, long seed) {
        this.type = 0;
        this.nonLethal = false;
        this.segments = new ArrayList();
        this.splitparents = new HashMap();
        this.world = world;
        this.start = jammervec;
        this.end = targetvec;
        this.seed = seed;
        this.rand = new Random(seed);
        this.numsegments0 = 1;
        this.increment = 1;
        this.length = this.end.copy().sub(this.start).length();
        this.particleMaxAge = 3 + this.rand.nextInt(3) - 1;
        this.multiplier = 1.0F;
        this.particleAge = -((int)(this.length * 3.0F));
        this.segments.add(new Segment(this.start, this.end));
    }

    public FXColoredLightningCommon(World world, Entity detonator, Entity target, long seed) {
        this(world, new WRVector3(detonator), new WRVector3(target), seed);
    }

    public FXColoredLightningCommon(World world, Entity detonator, Entity target, long seed, int speed) {
        this(world, new WRVector3(detonator), new WRVector3(target.posX, target.posY + (double)target.getEyeHeight() - 0.699999988079071, target.posZ), seed);
        this.increment = speed;
        this.multiplier = 0.4F;
    }

    public FXColoredLightningCommon(World world, TileEntity detonator, Entity target, long seed) {
        this(world, new WRVector3(detonator), new WRVector3(target), seed);
    }

    public FXColoredLightningCommon(World world, TileEntity detonator, double x, double y, double z, long seed) {
        this(world, new WRVector3(detonator), new WRVector3(x, y, z), seed);
    }

    public FXColoredLightningCommon(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi) {
        this(world, new WRVector3(x1, y1, z1), new WRVector3(x, y, z), seed);
        this.particleMaxAge = duration + this.rand.nextInt(duration) - duration / 2;
        this.multiplier = multi;
    }

    public FXColoredLightningCommon(World world, double x1, double y1, double z1, double x, double y, double z, long seed, int duration, float multi, int speed) {
        this(world, new WRVector3(x1, y1, z1), new WRVector3(x, y, z), seed);
        this.particleMaxAge = duration + this.rand.nextInt(duration) - duration / 2;
        this.multiplier = multi;
        this.increment = speed;
    }

    public void setMultiplier(float m) {
        this.multiplier = m;
    }

    public void fractal(int splits, float amount, float splitchance, float splitlength, float splitangle) {
        if (!this.finalized) {
            ArrayList oldsegments = this.segments;
            this.segments = new ArrayList();
            Segment prev = null;
            Iterator iterator = oldsegments.iterator();

            while(iterator.hasNext()) {
                Segment segment = (Segment)iterator.next();
                prev = segment.prev;
                WRVector3 subsegment = segment.diff.copy().scale(1.0F / (float)splits);
                BoltPoint[] newpoints = new BoltPoint[splits + 1];
                WRVector3 startpoint = segment.startpoint.point;
                newpoints[0] = segment.startpoint;
                newpoints[splits] = segment.endpoint;

                int i;
                WRVector3 splitrot;
                for(i = 1; i < splits; ++i) {
                    WRVector3 randoff = WRVector3.getPerpendicular(segment.diff).rotate(this.rand.nextFloat() * 360.0F, segment.diff);
                    randoff.scale((this.rand.nextFloat() - 0.5F) * amount);
                    splitrot = startpoint.copy().add(subsegment.copy().scale((float)i));
                    newpoints[i] = new BoltPoint(splitrot, randoff);
                }

                for(i = 0; i < splits; ++i) {
                    Segment next = new Segment(newpoints[i], newpoints[i + 1], segment.light, segment.segmentno * splits + i, segment.splitno);
                    next.prev = prev;
                    if (prev != null) {
                        prev.next = next;
                    }

                    if (i != 0 && this.rand.nextFloat() < splitchance) {
                        splitrot = WRVector3.xCrossProduct(next.diff).rotate(this.rand.nextFloat() * 360.0F, next.diff);
                        WRVector3 diff = next.diff.copy().rotate((this.rand.nextFloat() * 0.66F + 0.33F) * splitangle, splitrot).scale(splitlength);
                        ++this.numsplits;
                        this.splitparents.put(this.numsplits, next.splitno);
                        Segment split = new Segment(newpoints[i], new BoltPoint(newpoints[i + 1].basepoint, newpoints[i + 1].offsetvec.copy().add(diff)), segment.light / 2.0F, next.segmentno, this.numsplits);
                        split.prev = prev;
                        this.segments.add(split);
                    }

                    prev = next;
                    this.segments.add(next);
                }

                if (segment.next != null) {
                    segment.next.prev = prev;
                }
            }

            this.numsegments0 *= splits;
        }
    }

    public void defaultFractal() {
        this.fractal(2, this.length * this.multiplier / 8.0F, 0.7F, 0.1F, 45.0F);
        this.fractal(2, this.length * this.multiplier / 12.0F, 0.5F, 0.1F, 50.0F);
        this.fractal(2, this.length * this.multiplier / 17.0F, 0.5F, 0.1F, 55.0F);
        this.fractal(2, this.length * this.multiplier / 23.0F, 0.5F, 0.1F, 60.0F);
        this.fractal(2, this.length * this.multiplier / 30.0F, 0.0F, 0.0F, 0.0F);
        this.fractal(2, this.length * this.multiplier / 34.0F, 0.0F, 0.0F, 0.0F);
        this.fractal(2, this.length * this.multiplier / 40.0F, 0.0F, 0.0F, 0.0F);
    }

    private void calculateCollisionAndDiffs() {
        HashMap lastactivesegment = new HashMap();
        Collections.sort(this.segments, new SegmentSorter());
        int lastsplitcalc = 0;
        int lastactiveseg = 0;

        Segment segment;
        for(Iterator iterator = this.segments.iterator(); iterator.hasNext(); lastactiveseg = segment.segmentno) {
            segment = (Segment)iterator.next();
            if (segment.splitno > lastsplitcalc) {
                lastactivesegment.put(lastsplitcalc, lastactiveseg);
                lastsplitcalc = segment.splitno;
                lastactiveseg = (Integer)lastactivesegment.get(this.splitparents.get(segment.splitno));
            }
        }

        lastactivesegment.put(lastsplitcalc, lastactiveseg);
        lastsplitcalc = 0;
        lastactiveseg = (Integer)lastactivesegment.get(0);

        for(Iterator iterator = this.segments.iterator(); iterator.hasNext(); segment.calcEndDiffs()) {
            segment = (Segment)iterator.next();
            if (lastsplitcalc != segment.splitno) {
                lastsplitcalc = segment.splitno;
                lastactiveseg = (Integer)lastactivesegment.get(segment.splitno);
            }

            if (segment.segmentno > lastactiveseg) {
                iterator.remove();
            }
        }

    }

    public void finalizeBolt() {
        if (!this.finalized) {
            this.finalized = true;
            this.calculateCollisionAndDiffs();
            Collections.sort(this.segments, new SegmentLightSorter());
        }
    }

    public void onUpdate() {
        this.particleAge += this.increment;
        if (this.particleAge > this.particleMaxAge) {
            this.particleAge = this.particleMaxAge;
        }

    }

    public class SegmentSorter implements Comparator {
        final FXColoredLightningCommon this$0 = FXColoredLightningCommon.this;

        public int compare(Segment o1, Segment o2) {
            int comp = Integer.valueOf(o1.splitno).compareTo(o2.splitno);
            return comp == 0 ? Integer.valueOf(o1.segmentno).compareTo(o2.segmentno) : comp;
        }

        public int compare(Object obj, Object obj1) {
            return this.compare((Segment)obj, (Segment)obj1);
        }

        public SegmentSorter() {
        }
    }

    public class SegmentLightSorter implements Comparator {
        final FXColoredLightningCommon this$0 = FXColoredLightningCommon.this;

        public int compare(Segment o1, Segment o2) {
            return Float.compare(o2.light, o1.light);
        }

        public int compare(Object obj, Object obj1) {
            return this.compare((Segment)obj, (Segment)obj1);
        }

        public SegmentLightSorter() {
        }
    }

    public class Segment {
        public BoltPoint startpoint;
        public BoltPoint endpoint;
        public WRVector3 diff;
        public Segment prev;
        public Segment next;
        public WRVector3 nextdiff;
        public WRVector3 prevdiff;
        public float sinprev;
        public float sinnext;
        public float light;
        public int segmentno;
        public int splitno;
        final FXColoredLightningCommon this$0;

        public void calcDiff() {
            this.diff = this.endpoint.point.copy().sub(this.startpoint.point);
        }

        public void calcEndDiffs() {
            WRVector3 nextdiffnorm;
            WRVector3 thisdiffnorm;
            if (this.prev != null) {
                nextdiffnorm = this.prev.diff.copy().normalize();
                thisdiffnorm = this.diff.copy().normalize();
                this.prevdiff = thisdiffnorm.add(nextdiffnorm).normalize();
                this.sinprev = (float)Math.sin((double)(WRVector3.anglePreNorm(thisdiffnorm, nextdiffnorm.scale(-1.0F)) / 2.0F));
            } else {
                this.prevdiff = this.diff.copy().normalize();
                this.sinprev = 1.0F;
            }

            if (this.next != null) {
                nextdiffnorm = this.next.diff.copy().normalize();
                thisdiffnorm = this.diff.copy().normalize();
                this.nextdiff = thisdiffnorm.add(nextdiffnorm).normalize();
                this.sinnext = (float)Math.sin((double)(WRVector3.anglePreNorm(thisdiffnorm, nextdiffnorm.scale(-1.0F)) / 2.0F));
            } else {
                this.nextdiff = this.diff.copy().normalize();
                this.sinnext = 1.0F;
            }

        }

        public String toString() {
            return this.startpoint.point.toString() + " " + this.endpoint.point.toString();
        }

        public Segment(BoltPoint start, BoltPoint end, float light, int segmentnumber, int splitnumber) {
            this.this$0 = FXColoredLightningCommon.this;
            this.startpoint = start;
            this.endpoint = end;
            this.light = light;
            this.segmentno = segmentnumber;
            this.splitno = splitnumber;
            this.calcDiff();
        }

        public Segment(WRVector3 start, WRVector3 end) {
            this(FXColoredLightningCommon.this.new BoltPoint(start, new WRVector3(0.0, 0.0, 0.0)), FXColoredLightningCommon.this.new BoltPoint(end, new WRVector3(0.0, 0.0, 0.0)), 1.0F, 0, 0);
        }
    }

    public class BoltPoint {
        WRVector3 point;
        WRVector3 basepoint;
        WRVector3 offsetvec;
        final FXColoredLightningCommon this$0 = FXColoredLightningCommon.this;

        public BoltPoint(WRVector3 basepoint, WRVector3 offsetvec) {
            this.point = basepoint.copy().add(offsetvec);
            this.basepoint = basepoint;
            this.offsetvec = offsetvec;
        }
    }
}
