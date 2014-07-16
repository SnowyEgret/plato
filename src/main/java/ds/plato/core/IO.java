package ds.plato.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.vecmath.Point3i;

import com.google.gson.Gson;

import ds.plato.select.Selection;

//TODO refactor this class
@Deprecated
public class IO {

	public static String writeGroup(Point3i insertionPoint, List<Selection> selections, String fileName) throws IOException {
		String json = new Gson().toJson(new VoxelGroup(insertionPoint, selections));
		FileWriter writer = new FileWriter(fileName);
		writer.write(json);
		writer.close();
		return json;
	}

	public static VoxelGroup readGroup(String fileName) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		// Type listType = new TypeToken<ArrayList<Voxel>>() {
		// }.getType();
		VoxelGroup g = new Gson().fromJson(br, VoxelGroup.class);
		return g;
	}

	public static class VoxelGroup {
		public Point3i insertionPoint;
		public Voxel[] voxels;

		public VoxelGroup(Point3i insertionPoint, List<Selection> selections) {
			this.insertionPoint = insertionPoint;
			Voxel[] voxels = new Voxel[selections.size()];
			for (int i = 0; i < voxels.length; i++) {
				voxels[i] = new Voxel(selections.get(i));
			}
			this.voxels = voxels;
		}

		@Override
		public String toString() {
			return "Group [insertionPoint=" + insertionPoint + ", voxels=" + Arrays.toString(voxels) + "]";
		}
	}

	public static class Voxel {
		public String b;
		public int x, y, z, m;

		public Voxel(Selection s) {
			b = s.block.getUnlocalizedName().substring(5);
			// b = s.block.toString();
			x = s.x;
			y = s.y;
			z = s.z;
			m = s.metadata;
		}

		@Override
		public String toString() {
			return "Voxel [b=" + b + ", x=" + x + ", y=" + y + ", z=" + z + ", m=" + m + "]";
		}
	}

}
