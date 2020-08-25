package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GUI {
    private static final String OUTPUT_FOLDER = "temp/gui";
    private static final String OUTPUT_FILE = "index.html";
    private static final String CONTAINER_HTML_CODE = "<!DOCTYPE html>\n<html><head>\n  <title>Packing</title>\n  <script defer src=\"https://cdnjs.cloudflare.com/ajax/libs/three.js/110/three.min.js\"></script>\n  <script defer src=\"https://cdn.jsdelivr.net/npm/three@0.101.1/examples/js/controls/OrbitControls.js\"></script>\n  <script defer src=\"data.js\"></script>\n  <script defer src=\"script.js\"></script>\n  <style>html, body { margin: 0; padding: 0; overflow: hidden; }</style>\n</head>\n<body>\n</body></html>";
    private static final String SCRIPT_FILE_CODE = "\"use strict\";\nlet camera, scene, renderer;\nfunction onCommonWindowResize() {\n    camera.aspect = window.innerWidth / window.innerHeight;\n    camera.updateProjectionMatrix();\n    renderer.setSize(window.innerWidth, window.innerHeight);\n}\nfunction animate() {\n    requestAnimationFrame(animate);\n    renderer.render(scene, camera);\n}\nfunction move_mesh(mesh, l, h, d, x = 0, y = 0, z = 0) {\n    mesh.position.set((l / 2) + x, (h / 2) + y, (d / 2) + z);\n}\nfunction create_mesh(l, h, d, c, ce, o) {\n    const group = new THREE.Group();\n    const mesh = new THREE.Mesh(new THREE.BoxBufferGeometry(l, h, d), new THREE.MeshPhongMaterial( {color: c, transparent: true, opacity: o, side: THREE.DoubleSide, depthWrite: false } ));\n    group.add(mesh);\n    group.add(new THREE.LineSegments( new THREE.EdgesGeometry( mesh.geometry ), new THREE.MeshBasicMaterial( {color: ce, transparent: true, opacity: o} ) ));\n    return group;\n}\nfunction init() {\n    scene = new THREE.Scene();\n    scene.background = new THREE.Color(0xFFFFFF);\n    const group = new THREE.Group();\n    const raw = data['raw'];\n    const max_size = Math.max(raw.length, raw.height, raw.depth);\n    let box = create_mesh(raw.length, raw.height, raw.depth, raw.color, raw.color_edge, .1);\n    move_mesh(box, raw.length, raw.height, raw.depth);\n    group.add(box);\n    for (let item of data['items']) {\n        box = create_mesh(item.length, item.height, item.depth, item.color, item.color_edge, 0.4);\n        move_mesh(box, item.length, item.height, item.depth, item.x, item.y, item.z);\n        group.add(box);\n    }\n    scene.add(group);\n    if (data['axes']) { scene.add(new THREE.AxesHelper(max_size * 1.2)); }\n    camera = new THREE.PerspectiveCamera(90, window.innerWidth / window.innerHeight, 0.01, max_size * 10);\n    camera.position.set(max_size, max_size, max_size);\n    renderer = new THREE.WebGLRenderer({ antialias: true,  alpha: true,  preserveDrawingBuffer: true});\n    renderer.setSize(window.innerWidth, window.innerHeight);\n    renderer.render(scene, camera);\n    document.body.appendChild(renderer.domElement);\n    const controls = new THREE.OrbitControls(camera, renderer.domElement);\n    controls.enableDamping = true;\n    controls.dampingFactor = 0.25;\n    controls.enableZoom = true;\n    controls.enablePan = true;\n    const light = new THREE.DirectionalLight(0xFFFFFF, .5);\n    light.position.set(max_size, 0, max_size);\n    light.shadow.mapSize.width = 1024;\n    light.shadow.mapSize.height = 1024;\n    scene.add(light);\n    window.addEventListener(\"resize\", onCommonWindowResize);\n    animate();\n}\ninit();";

    public GUI() {
    }

    private static void createDataFile(JSONObject delivery) throws IOException {
        String itemsStr = "";
        JSONArray items = (JSONArray)delivery.get("items");

        JSONObject item;
        for(Iterator var3 = items.iterator(); var3.hasNext(); itemsStr = itemsStr + String.format("{ length: %d, height: %d, depth: %d, color: '%s', color_edge: '%s', x: %d, y: %d, z: %d, },", (Long)item.get("length"), (Long)item.get("height"), (Long)item.get("depth"), (String)item.get("color"), (String)item.get("color"), (Long)item.get("x"), (Long)item.get("y"), (Long)item.get("z"))) {
            Object i = var3.next();
            item = (JSONObject)i;
        }

        String data = String.format("data={ axes: %s, raw: { length: %d, height: %d, depth: %d, color: '%s', color_edge: '%s' }, items: [ %s ] }", "true", (Long)delivery.get("length"), (Long)delivery.get("height"), (Long)delivery.get("depth"), (String)delivery.get("color"), (String)delivery.get("color"), itemsStr);
        FileWriter file = new FileWriter("temp/gui/data.js");

        try {
            file.write(data);
        } catch (Throwable var8) {
            try {
                file.close();
            } catch (Throwable var7) {
                var8.addSuppressed(var7);
            }

            throw var8;
        }

        file.close();
    }

    public static void render(String filepath) throws FileNotFoundException, IOException, ParseException {
        File directory = new File("temp/gui");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        FileWriter file = new FileWriter("temp/gui/script.js");

        try {
            file.write("\"use strict\";\nlet camera, scene, renderer;\nfunction onCommonWindowResize() {\n    camera.aspect = window.innerWidth / window.innerHeight;\n    camera.updateProjectionMatrix();\n    renderer.setSize(window.innerWidth, window.innerHeight);\n}\nfunction animate() {\n    requestAnimationFrame(animate);\n    renderer.render(scene, camera);\n}\nfunction move_mesh(mesh, l, h, d, x = 0, y = 0, z = 0) {\n    mesh.position.set((l / 2) + x, (h / 2) + y, (d / 2) + z);\n}\nfunction create_mesh(l, h, d, c, ce, o) {\n    const group = new THREE.Group();\n    const mesh = new THREE.Mesh(new THREE.BoxBufferGeometry(l, h, d), new THREE.MeshPhongMaterial( {color: c, transparent: true, opacity: o, side: THREE.DoubleSide, depthWrite: false } ));\n    group.add(mesh);\n    group.add(new THREE.LineSegments( new THREE.EdgesGeometry( mesh.geometry ), new THREE.MeshBasicMaterial( {color: ce, transparent: true, opacity: o} ) ));\n    return group;\n}\nfunction init() {\n    scene = new THREE.Scene();\n    scene.background = new THREE.Color(0xFFFFFF);\n    const group = new THREE.Group();\n    const raw = data['raw'];\n    const max_size = Math.max(raw.length, raw.height, raw.depth);\n    let box = create_mesh(raw.length, raw.height, raw.depth, raw.color, raw.color_edge, .1);\n    move_mesh(box, raw.length, raw.height, raw.depth);\n    group.add(box);\n    for (let item of data['items']) {\n        box = create_mesh(item.length, item.height, item.depth, item.color, item.color_edge, 0.4);\n        move_mesh(box, item.length, item.height, item.depth, item.x, item.y, item.z);\n        group.add(box);\n    }\n    scene.add(group);\n    if (data['axes']) { scene.add(new THREE.AxesHelper(max_size * 1.2)); }\n    camera = new THREE.PerspectiveCamera(90, window.innerWidth / window.innerHeight, 0.01, max_size * 10);\n    camera.position.set(max_size, max_size, max_size);\n    renderer = new THREE.WebGLRenderer({ antialias: true,  alpha: true,  preserveDrawingBuffer: true});\n    renderer.setSize(window.innerWidth, window.innerHeight);\n    renderer.render(scene, camera);\n    document.body.appendChild(renderer.domElement);\n    const controls = new THREE.OrbitControls(camera, renderer.domElement);\n    controls.enableDamping = true;\n    controls.dampingFactor = 0.25;\n    controls.enableZoom = true;\n    controls.enablePan = true;\n    const light = new THREE.DirectionalLight(0xFFFFFF, .5);\n    light.position.set(max_size, 0, max_size);\n    light.shadow.mapSize.width = 1024;\n    light.shadow.mapSize.height = 1024;\n    scene.add(light);\n    window.addEventListener(\"resize\", onCommonWindowResize);\n    animate();\n}\ninit();");
        } catch (Throwable var11) {
            try {
                file.close();
            } catch (Throwable var7) {
                var11.addSuppressed(var7);
            }

            throw var11;
        }

        file.close();
        String html = "<!DOCTYPE html>\n<html><head>\n  <title>Packing</title>\n  <script defer src=\"https://cdnjs.cloudflare.com/ajax/libs/three.js/110/three.min.js\"></script>\n  <script defer src=\"https://cdn.jsdelivr.net/npm/three@0.101.1/examples/js/controls/OrbitControls.js\"></script>\n  <script defer src=\"data.js\"></script>\n  <script defer src=\"script.js\"></script>\n  <style>html, body { margin: 0; padding: 0; overflow: hidden; }</style>\n</head>\n<body>\n</body></html>";
        FileReader reader = new FileReader(filepath);

        try {
            Object obj = (new JSONParser()).parse(reader);
            createDataFile((JSONObject)obj);
        } catch (Throwable var10) {
            try {
                reader.close();
            } catch (Throwable var6) {
                var10.addSuppressed(var6);
            }

            throw var10;
        }

        reader.close();
        FileWriter file = new FileWriter("temp/gui/index.html");

        try {
            file.write(html);
            Desktop.getDesktop().browse((new File("temp/gui/index.html")).toURI());
        } catch (Throwable var9) {
            try {
                file.close();
            } catch (Throwable var8) {
                var9.addSuppressed(var8);
            }

            throw var9;
        }

        file.close();
    }
}
